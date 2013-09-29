package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import com.mongodb.casbah.Imports._
object ClientEntity {
	case class All
	case class Client(email:String, name:String, about:String, friends:List[String])
}

// class that contains methods to perform specific tasks against the datastore
class ClientEntity(datastore:Datastore) extends Actor {
	
	import chatclient.sink.Interceptor._
	import ClientEntity._

	val pack = new RemotePackager

	def receive = {
		case All =>
			sender ! datastore.find().map(x => parseClient(x)).toList
		case _ => println("unrecognised message")
	}

	private def parseClient(obj:DBObject):Client = {

		implicit def as(obj:DBObject) = new As(obj)
		
		Client(
			obj string("email"), 
			obj string("name"),
			obj string("about"), 
			obj list("friends")
		)
	}
}

