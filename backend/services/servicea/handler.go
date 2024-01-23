package main

import (
	"context"

	pb "pro.herlian/vihara/proto"
)

type Handler struct {
	pb.UnimplementedServiceAServer
}

func (h *Handler) Sum(_ context.Context, rqst *pb.SumRequest) (response *pb.SumResponse, err error) {
	response = &pb.SumResponse{
		Result: rqst.GetA() + rqst.GetB(),
	}
	return
}