package chatclient.sink

import akka.actor.{ Actor, Props, ActorSystem}
import com.typesafe.config.ConfigFactory

object Server {
	
	def main(args: Array[String]) {
		
		// register server actor
		val system = ActorSystem("Actim", ConfigFactory.load.getConfig("actim"))
		val remoteActor = system.actorOf(Props[Interceptor], name = "remoteActor")
	}
}