FROM golang:1.21.5-alpine3.17 AS builder

WORKDIR /app
COPY . .
RUN go mod download
RUN CGO_ENABLED=0 GOOS=linux go build -o main

FROM scratch

COPY --from=builder /app/main /app/main
ENTRYPOINT ["/app/main"]