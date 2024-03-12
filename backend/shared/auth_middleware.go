package shared

import (
	"context"
	"crypto/rsa"
	"fmt"
	"net/http"
	"strings"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
	"github.com/lestrrat-go/jwx/v2/jwk"
)

func NewJWKSet(jwkUrl string) (jwk.Set, error) {
	jwkCache := jwk.NewCache(context.Background())

	err := jwkCache.Register(jwkUrl)
	if err != nil {
		return nil, err
	}

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	_, err = jwkCache.Refresh(ctx, jwkUrl)
	if err != nil {
		return nil, err
	}
	return jwk.NewCachedSet(jwkCache, jwkUrl), nil
}

const (
	googleIssuer       = "https://accounts.google.com"
	appleIssuer        = "https://appleid.apple.com"
	googlePublicKeyURL = "https://www.googleapis.com/oauth2/v3/certs"
	applePublicKeyURL  = "https://appleid.apple.com/auth/keys"
)

func getPublicKey(token *jwt.Token) (interface{}, error) {
	var url string
	issuer, err := token.Claims.GetIssuer()
	if err != nil {
		return nil, err
	}
	switch issuer {
	case googleIssuer:
		url = googlePublicKeyURL
	case appleIssuer:
		url = applePublicKeyURL
	default:
		return nil, fmt.Errorf("unknown issuer")
	}
	keySet, err := NewJWKSet(url)
	if err != nil {
		return nil, err
	}

	keyID, _ := token.Header["kid"].(string)
	if key, ok := keySet.LookupKeyID(keyID); ok {
		var result rsa.PublicKey
		err := key.Raw(&result)
		if err != nil {
			return nil, err
		}
		return &result, nil
	}

	return nil, fmt.Errorf("unable to find key")
}

func verifyToken(bearerToken string) (interface{}, error) {
	authToken := strings.TrimPrefix(bearerToken, "Bearer ")
	token, err := jwt.Parse(authToken, getPublicKey)
	if err != nil {
		return nil, err
	}
	if !token.Valid {
		fmt.Println("Masuk Invalid Token")
		return nil, fmt.Errorf("invalid token")
	}
	if claims, ok := token.Claims.(jwt.MapClaims); ok {
		return claims, nil
	}
	return nil, fmt.Errorf("invalid token")
}

func TokenAuthMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		token := c.Request.Header.Get("authorization")
		result, err := verifyToken(token)
		if err != nil {
			fmt.Printf("Error: %v\n", err)
			c.AbortWithStatusJSON(http.StatusUnauthorized, nil)
		}
		c.Set("token", result)
	}
}
