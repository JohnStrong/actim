package chatclient.source

import com.typesafe.config.ConfigFactory

import akka.actor.{Actor, ActorSystem, Props}

import akka.util.Timeout
import scala.concurrent.duration._

import swing._

import chatclient.ccd.PatternPackage._

/**
* handle UI events and pass them on to the RemoteHandler Actor for processing
**/

case class Client(email:String, name:String)

object Client {

	// start connection remote actor through remote lookup class
	val system = ActorSystem("clientsystem", 
	ConfigFactory.load.getConfig("clientsystem"))
	val path = "akka.tcp://Actim@127.0.0.1:2552/user/remoteActor"
	
	var distributer = system.actorOf(Props(classOf[Distributer], 
	path), name = "creationActor")

	
	/** outgoing
	*/

	// handle login event
	def login(email:String):Unit =
		distributer ! Login(email)

	// publish message from client to the server
	def sendMessage(message:String):Unit = {
		println(message)
		println(Some(Client))
	}

	
	/** incoming
	*/

	// update gui with chat window upon successful login
	def clientReady(email:String, name:String):Unit = {
		
		Client(email, name)
		println(ClientInterface.top)
	}
}