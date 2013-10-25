package chatclient.source

import com.typesafe.config.ConfigFactory

import akka.actor.{Actor, ActorSystem, Props, ActorRef}

import akka.util.Timeout
import scala.concurrent.duration._

import swing._

import scala.collection._

import chatclient.ccd.PatternPackage._

/**
* handle UI events and pass them on to the RemoteHandler Actor for processing
**/
case class User(email:String, name:String)

/**
 * Client interface to message distributer wrapper
 */
object Client {

	// this client instance
	private var thisClient:User = null

	val system = ActorSystem("clientsystem", 
		ConfigFactory.load.getConfig("clientsystem"))
	val SERVER_PATH = "akka.tcp://Actim@127.0.0.1:2552/user/remoteActor"
	val distributer = system.actorOf(Props(classOf[Distributer], 
		SERVER_PATH), name = "creationActor")

	/** 
	 * outgoing
	 */
	def login(email:String):Unit = 
		distributer ! Login(email)

	def sendMessage(message:String):Unit = {

		distributer ! SendMessage(thisClient.name, message)
		ClientInterface.mainInput.text = ""
	}
	
	/** 
	 * incoming
	 */
	// update ui events on successful login
	def clientReady(email:String, name:String):Unit = {

		thisClient = User(email, name)

		ClientInterface.chatFeed.text = "Welcome " + name
		ClientInterface.mainInput.text = "broadcast to fellow clients..."
		ClientInterface.mainEvtListener.text = "Send"
	}

	def displayOfflineMessages(messages: mutable.Map[String, String]):Unit = {
		messages.foreach(m => {
			ClientInterface.chatFeed.text += "\n" + m._1 + ":\t" + m._2
		})
	}

 	def displayMessage(from:String, message:String):Unit =
 		ClientInterface.chatFeed.text += "\n" + from + ":\t" + message
}