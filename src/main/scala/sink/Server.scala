package chatclient.sink

import akka.actor.Actor
import akka.actor.Props

class Server extends Actor {

	// create chatClient actor
	override def preStart(): Unit = {
		val chatClient = context.actorOf(Props[Remote], "chatClient")
	}

	def receive = {
		case Remote.Done(x) => context.stop(self)
	}
}