package chatclient.sink

import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props

class ServerStartup {

	import Remote._

	val system = ActorSystem("actim", ConfigFactory.load.getConfig("actim"))
	val remoteActor = system.actorOf(Props[Remote], "remoteActor")
}

object Server {
	
	// entry point for the remote
	def main(args: Array[String]) {
		new ServerStartup
	}
}