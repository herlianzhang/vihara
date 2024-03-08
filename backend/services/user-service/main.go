package main

import (
	"context"
	"fmt"
	"os"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"github.com/jackc/pgx/v5"
	"github.com/joho/godotenv"
	"pro.herlian.vihara/backend/services/user-service/db"
	"pro.herlian.vihara/backend/services/user-service/repository"
	"pro.herlian.vihara/backend/services/user-service/rest"
)

func main() {
	r := gin.Default()
	ctx := context.Background()
	if err := godotenv.Load("./backend/services/user-service/local.env"); err != nil {
		fmt.Println(os.Getwd())
		fmt.Printf("Error loading .env file %v\n", err)
		return
	}
	DB_URL := os.Getenv("DB_URL")
	conn, err := pgx.Connect(ctx, DB_URL)
	if err != nil {
		fmt.Println("Error to init db")
		return
	}
	defer conn.Close(ctx)

	queries := db.New(conn)

	repository := repository.NewUserRepository(queries)
	rest.NewUserHandler(r, *repository, validator.New())

	r.Run()
}
