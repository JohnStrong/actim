package chatclient.source

import com.typesafe.config.ConfigFactory

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/**
* handle UI events and pass them on to the RemoteHandler Actor for processing
**/
class Client {

	import chatclient.sink.Interceptor._
	import Distributer._

	// start connection remote actor through remote lookup class
	val system = ActorSystem("clientsystem", 
		ConfigFactory.load.getConfig("clientsystem"))
	val path = "akka.tcp://Actim@127.0.0.1:2552/user/remoteActor"
	val distributer = system.actorOf(Props(classOf[Distributer], 
		path), name = "creationActor")

	// handle ui events
	def login(email:String):Unit = distributer ! Login(email)
}