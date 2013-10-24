package chatclient.source

import com.typesafe.config.ConfigFactory

import akka.actor.{Actor, ActorSystem, Props, ActorRef}

import akka.util.Timeout
import scala.concurrent.duration._

import swing._

import chatclient.ccd.PatternPackage._

/**
* handle UI events and pass them on to the RemoteHandler Actor for processing
**/

case class Client(email:String, name:String)

object Client {

	val system = ActorSystem("clientsystem", 
	ConfigFactory.load.getConfig("clientsystem"))
	val path = "akka.tcp://Actim@127.0.0.1:2552/user/remoteActor"

	val distributer = system.actorOf(Props(classOf[Distributer], 
	path), name = "creationActor")
	
	/** 
	 * outgoing
	 */
	// handle login event
	def login(email:String):Unit = {
		distributer ! Login(email)
	}

	// publish message from client to the server
	def sendMessage(message:String):Unit = {

		//todo
		println(Some(Client))
	}
	
	/** 
	 * incoming
	 */
	// update ui events on successful login
	def clientReady(email:String, name:String):Unit = {

		ClientInterface.mainInput.text = "Hello Chat!"
		ClientInterface.mainEvtListener.text = "Send"
	}
}