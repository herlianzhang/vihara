package main

import (
	"context"
	"time"

	pb "pro.herlian.vihara/proto"
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

func (h *Handler) SumStream(rqst *pb.SumRequest, stream pb.ServiceA_SumStreamServer) error {
	for i := 0; i < 10; i++ {
		time.Sleep(2 * time.Second)
		if err := stream.Send(&pb.SumResponse{
			Result: int32(i) + rqst.GetA() + rqst.GetB(),
		}); err != nil {
			return err
		}
	}
	return nil
}