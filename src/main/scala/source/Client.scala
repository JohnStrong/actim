package chatclient.source

import com.typesafe.config.ConfigFactory

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object Client {

	/**
	* handle UI events and pass them on to the RemoteHandler Actor for processing
	**/
	class Client {

		import chatclient.sink.Interceptor._
		import Distributer._

		val path = "akka.tcp://remoteSystem@127.0.0.1:2552/actim"

		// start connection remote actor through remote lookup class
		val system = ActorSystem("clientsystem", ConfigFactory.load.getConfig("clientsystem"))
		val clientActor = system.actorOf(Props(classOf[Distributer], 
			path), "creationActor")

		// handle ui events
		def login(email:String):Unit = clientActor ! Login(email)
	}
}