package repository

import (
	"context"
	"errors"

	"github.com/jackc/pgx/v5"
	"github.com/jackc/pgx/v5/pgtype"
	"pro.herlian.vihara/backend/services/user-service/db"
	"pro.herlian.vihara/backend/services/user-service/model"
)

var (
	ErrUserAlreadyExist = errors.New("user already exists")
)

type UserRepository struct {
	Queries *db.Queries
}

func NewUserRepository(queries *db.Queries) *UserRepository {
	return &UserRepository{Queries: queries}
}

func (r *UserRepository) GetUsers() ([]db.User, error) {
	ctx := context.Background()
	users, err := r.Queries.ListUsers(ctx)
	if err != nil {
		return nil, err
	}
	return users, nil
}

func (r *UserRepository) CreateUser(id string, p model.CreateUserParams) (*db.User, error) {
	ctx := context.Background()
	_, err := r.Queries.GetUserByGoogleId(ctx, pgtype.Text{String: id, Valid: true})
	if err != nil {
		if err != pgx.ErrNoRows {
			user, err := r.Queries.CreateUser(ctx, db.CreateUserParams{
				Username: p.Username,
				GoogleID: pgtype.Text{String: id, Valid: true},
				Name:     p.Name,
				Email:    p.Email,
				Avatar:   p.Avatar,
			})
			if err != nil {
				return nil, err
			}
			return &user, nil
		} else {
			return nil, err
		}
	}
	return nil, ErrUserAlreadyExist
}

func (r *UserRepository) CheckUser(id string) (*db.User, error) {
	ctx := context.Background()
	user, err := r.Queries.GetUserByGoogleId(ctx, pgtype.Text{String: id, Valid: true})
	if err != nil {
		return nil, err
	}
	return &user, nil
}
