package chatclient.source

import com.typesafe.config.ConfigFactory

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object Client {

	// send/receive messages to/form remote actor
	class Client {

		import chatclient.sink.Remote._
		import RemoteHandler._

		val PATH = "akka.tcp://RemoteSystem@127.0.0.1:8080/user/remote.sink.chatclient"

		// start connection remote actor through remote lookup class
		val system = ActorSystem("ClientSystem")
		val remoteActor = system.actorOf(Props[Remote], name = "remoteSink")
		val clientActor = system.actorOf(Props(classOf[RemoteHandler], remoteActor), name = "creationActor")

		// handle ui events
		def login(email:String):Unit = {
			clientActor ! Login(email)
		}
	}
}