package chatclient.sink

import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props

class ServerStartup {

	// start remote actor listening on port 2552
	val system = ActorSystem("Actim", ConfigFactory.load.getConfig("actim"))
	val remoteActor = system.actorOf(Props[Interceptor], name = "remoteActor")
}

object Server {
	
	// entry point for the remote
	def main(args: Array[String]) {
		new ServerStartup
	}
}