package main

import (
	"context"
	"errors"
	"fmt"
	"math"
	"net/http"
	"reflect"
	"runtime"
	"sort"
	"strconv"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
	"github.com/lestrrat-go/jwx/v2/jwk"
)

type ChangeResponse struct {
	Message string
	Change  []DenominationCounter
}

type DenominationCounter struct {
	Denomination string
	Count      int
}

var verifyKey interface{}

func main() {
	r := gin.Default()
	r.GET("/make-change", isAuthorized(makeChange))
  r.GET("/panic", isAuthorized(panic))
	r.Run(":8081")
}

func panic(c *gin.Context) {
  message := ""
  var status int

  switch c.Request.Method {
  case "POST":
    message = "We've called the police!"
    status = http.StatusOK
  default:
    message = "Only POST method is supported."
    status = http.StatusNotImplemented
  }

  responseObject := make(map[string]string)
  responseObject["message"] = message

  c.JSON(status, responseObject)
}

func isAuthorized(endpoint func(c *gin.Context)) gin.HandlerFunc {
  return func(c *gin.Context) {
    reqToken := ""
    tokenCookie, err := c.Cookie("app.at")
    if err != nil {
      if errors.Is(err, http.ErrNoCookie) {
        reqToken = c.GetHeader("Authorization")
        splitToken := strings.Split(reqToken, "Bearer ")
        reqToken = splitToken[1]
      }
    } else {
      reqToken = tokenCookie
    }

    responseObject := make(map[string]string)
    if reqToken == "" {
      responseObject["message"] = "No Token provided"
      c.JSON(http.StatusUnauthorized, responseObject)
    } else {
      token, err := jwt.Parse(reqToken, func(token *jwt.Token) (interface{}, error) {
        if _, ok := token.Method.(*jwt.SigningMethodRSA); !ok {
          return nil, fmt.Errorf("invalid signing method")
        }
        // tokenAuds, err := token.Claims.(jwt.MapClaims).GetAudience()
        // if err != nil {
        //   return nil, fmt.Errorf("invalid aud")
        // }
        // aud := "e9fdb985-9173-4e01-9d73-ac2d60d1dc8e"
        // if !slices.Contains(tokenAuds, aud) {
        //   return nil, fmt.Errorf("invalid aud")
        // }

        // iss := "http://localhost:9011"
        // tokenIss, err := token.Claims.(jwt.MapClaims).GetIssuer()
        // if err != nil || iss != tokenIss {
        //   return nil, fmt.Errorf("invalid iss")
        // }
        setPublicKey(token.Header["kid"].(string))
        return verifyKey, nil
      })
      if err != nil {
        fmt.Println(err.Error())
        return
      }

      if token.Valid {
        endpoint(c)
        // var roles = token.Claims.(jwt.MapClaims)["roles"]
        // var validRoles []string

        // switch pageToGet := GetFunctionName(endpoint); pageToGet {
        // case "main.panic":
        //   validRoles = []string{"teller"}
        // case "main.makeChange":
        //   validRoles = []string{"customer", "teller"}
        // }

        // result := containsRole([]string{roles.([]interface{})[0].(string)}, validRoles)

        // if len(result) > 0 {
        //   endpoint(c)
        // } else {
        //   responseObject := make(map[string]string)
        //   responseObject["message"] = "Proper role not found for user"
        //   c.JSON(http.StatusUnauthorized, responseObject)
        // }
      }
    }
  }
}

func containsRole(roles []string, rolesToCheck []string) []string {
  intersection := make([]string, 0)

  set := make(map[string]bool)

  for _, role := range roles {
    set[role] = true
  }

  for _, role := range rolesToCheck {
    if set[role] {
      intersection = append(intersection, role)
    }
  }

  return intersection
}

func GetFunctionName(i interface{}) string {
  return runtime.FuncForPC(reflect.ValueOf(i).Pointer()).Name()
}

func setPublicKey(kid string) {
  jwksURL := "http://localhost:8080/realms/vihara/protocol/openid-connect/certs"
  keySet, _ := jwk.Fetch(context.Background(), jwksURL)
  key, _ := keySet.LookupKeyID(kid)
  key.Raw(&verifyKey)
}

// func setPublicKey(kid string) {
//   // if verifyKey == nil {
//   response, err := http.Get("http://localhost:9011/api/jwt/public-key?kid=" + kid)
//   if err != nil {
//     log.Fatalln(err)
//   }

//   responseData, err := io.ReadAll(response.Body)
//   if err != nil {
//     log.Fatal(err)
//   }

//   var publicKey map[string]interface{}

//   json.Unmarshal(responseData, &publicKey)

//   var publicKeyPEM = publicKey["publicKey"].(string)

//   var verifyBytes = []byte(publicKeyPEM)
//   verifyKey, err = jwt.ParseRSAPublicKeyFromPEM(verifyBytes)

//   if err != nil {
//     log.Fatalln("problem retreving public key")
//   }
//   // }
// }

func makeChange(c *gin.Context) {
  response := ChangeResponse{}

  switch c.Request.Method {
  case "GET":
    var total = c.Query("total")
    var message = "We can make change using"
    remainingAmount, err := strconv.ParseFloat(total, 64)
    if err != nil {
      responseObject := make(map[string]string)
      responseObject["message"] = "Problem converting the submitted value to a decimal. Value submitted: " + total
      c.JSON(http.StatusBadRequest, responseObject)
      return
    }

    coins := make(map[float64]string)
    coins[.25] = "quarters"
    coins[.10] = "dimes"
    coins[.05] = "nickels"
    coins[.01] = "pennies"

    denominationOrder := make([]float64, 0, len(coins))
    for value := range coins {
      denominationOrder = append(denominationOrder, value)
    }

    sort.Slice(denominationOrder, func(i, j int) bool {
      return denominationOrder[i] > denominationOrder[j]
    })

    for counter := range denominationOrder {
      value := denominationOrder[counter]
      coinName := coins[value]
      coinCount := int(remainingAmount / value)
      remainingAmount = math.Round(remainingAmount * 100) / 100

      message += " " + strconv.Itoa(coinCount) + " " + coinName
      denominationCount := DenominationCounter{}
      denominationCount.Count = coinCount
      denominationCount.Denomination = coinName
      response.Change = append(response.Change, denominationCount)
    }
    response.Message = message

    c.JSON(http.StatusOK, response)

  default:
    responseObject := make(map[string]string)
    responseObject["message"] = "Only GET method is supported."
    c.JSON(http.StatusNotImplemented, responseObject)
  }
}