package chatclient.sink

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object Server {
	
	import Remote._

	// entry point for the remote
	def main(args: Array[String]) {
		
		val system = ActorSystem("remoteSystem")
	}
}