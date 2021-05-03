package main

import (
	"vfserver/game"
	"vfserver/network"
	// "bufio"
	// "fmt"
	// "io"
	// "net"
	// "time"
)

func main() {
	go game.Init()
	network.Init()

}
