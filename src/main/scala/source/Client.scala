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
	// verification of email
	def login(email:String):Unit = 
		distributer ! Login(email)

	// sends message to server with email
	def sendMessage(message:String):Unit = 
		distributer ! Message("j.strong1@nuigalway.ie", message)
	
	/** 
	 * incoming
	 */
	// update ui events on successful login
	def clientReady(email:String, name:String):Unit = {

		ClientInterface.chatFeed.text = "Welcome " + name

		ClientInterface.mainInput.text = "broadcast to fellow clients..."
		ClientInterface.mainEvtListener.text = "Send"
	}
}