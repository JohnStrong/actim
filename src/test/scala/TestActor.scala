package chatclient.test.actors

import akka.actor.Actor
import akka.actor.Props

import chatclient.sink.Remote

// Actors used to communicate with the remote
object Test {
	case class Login(email: String)
}

class Tester extends Actor {
	// todo
}