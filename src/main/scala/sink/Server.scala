package chatclient.sink

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object Server {

	// entry point for the remote
	def main(args: Array[String]) {
		val system = ActorSystem("ChatClient")
		val remote = system.actorOf(Props[Remote], "remote.sink.chatclient")
	}
}