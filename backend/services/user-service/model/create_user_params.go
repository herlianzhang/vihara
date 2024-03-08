package model

import "github.com/jackc/pgx/v5/pgtype"

type CreateUserParams struct {
	Username string      `form:"username" validate:"required"`
	Name     string      `form:"name" validate:"required"`
	Avatar   pgtype.Text `form:"avatar"`
	Email    pgtype.Text `form:"email"`
}