package chatclient.source

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object Client {

	// send/receive messages to/form remote actor
	class Client {

		import chatclient.sink.Remote._
		import RemoteLookup._

		val PATH = "akka.tcp://RemoteSystem@127.0.0.1:8080/User1-PC/remote.sink.chatclient"

		// start connection remote actor through remote lookup class
		val system = ActorSystem("ClientSystem")
		val actor = system.actorOf(Props(classOf[RemoteLookup], PATH), "chatclient.source.remoteLookup")

		// handle ui events
		def login(email:String):Unit = {
			actor ! Login(email)
		}
	}
}