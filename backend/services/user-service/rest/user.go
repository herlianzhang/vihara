package rest

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"github.com/golang-jwt/jwt/v5"
	"github.com/jackc/pgx/v5"
	"pro.herlian.vihara/backend/services/user-service/model"
	"pro.herlian.vihara/backend/services/user-service/repository"
	"pro.herlian.vihara/backend/shared"
)

type UserService interface {
	GetUsers(ctx *gin.Context)
	CreateUser(ctx *gin.Context)
	CheckUser(ctx *gin.Context)
}

type UserHandler struct {
	UserRepository repository.UserRepository
	Validate       *validator.Validate
}

func NewUserHandler(
	r *gin.Engine,
	userRepository repository.UserRepository,
	validate *validator.Validate,
) {
	handler := &UserHandler{UserRepository: userRepository, Validate: validate}
	r.GET("/users", handler.GetUsers)
	authorized := r.Group("/")
	authorized.Use(shared.TokenAuthMiddleware())
	authorized.POST("/createUser", handler.CreateUser)
	authorized.POST("/checkUser", handler.CheckUser)
}

func (u *UserHandler) GetUsers(c *gin.Context) {
	dbUsers, err := u.UserRepository.GetUsers()
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, dbUsers)
}

func (u *UserHandler) CreateUser(c *gin.Context) {
	var p model.CreateUserParams

	if err := c.Bind(&p); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	err := u.Validate.Struct(p)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	token := c.MustGet("token").(jwt.MapClaims)
	id := token["sub"].(string)

	dbUser, err := u.UserRepository.CreateUser(id, p)
	if err != nil {
		if err == repository.ErrUserAlreadyExist {
			c.JSON(http.StatusConflict, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, dbUser)
}

func (u *UserHandler) CheckUser(c *gin.Context) {
	token := c.MustGet("token").(jwt.MapClaims)
	id := token["sub"].(string)
	dbuser, err := u.UserRepository.CheckUser(id)
	if err != nil {
		if err == pgx.ErrNoRows {
			c.JSON(http.StatusNotFound, gin.H{"error": "user not found"})
			return
		}
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
	}
	c.JSON(http.StatusOK, dbuser)
}
