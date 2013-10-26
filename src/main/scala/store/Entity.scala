package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._

import chatclient.ccd.PatternPackage._

object EntityMessages {

	case class All
	case class Client(email:String, name:String)
	case class Message(name:String, body:String)
	case class Insert(name: String, message:String)
}

trait Entity {

	implicit def as(obj:DBObject) = new As(obj)
}

class ClientEntity(datastore:Datastore) extends Entity with Actor {
	
	import EntityMessages._

	def receive = {

		case All =>
			sender ! datastore.find().map(x => parseClient(x)).toList

		case _ =>
			println("unrecognied message in Entity")
	}

	private def parseClient(clientObj:DBObject):Client = {

		Client(
			clientObj string("email"), 
			clientObj string("name")
		)
	}
}

class MessageEntity(datastore:Datastore) extends Entity with Actor {

	import EntityMessages._

	def receive = {

		case All =>
			sender ! datastore.find().map(x => parseMessage(x)).toList

		case Insert(from, message) =>
			datastore.insert(messageToDBObject(from, message))

		case _ => 
			println("unrecognied message in Entity")
	}

	private def parseMessage(messageObj:DBObject):Message = {

		Message(
			messageObj string("name"),
			messageObj string("message")
		)
	}

	private def messageToDBObject(from:String, message:String):DBObject = {
		
		MongoDBObject(
			"name" -> from,
			"message" -> message
		)
	}
}

