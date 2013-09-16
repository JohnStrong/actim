package chatclient.store

import com.mongodb.casbah.Imports._

trait Datastore {

	// open chat client datastore
	protected val DB = MongoClient()("instant_messenger")

	def getOne(s: String):Option[DBObject]

	def getAll():List[DBObject]
}

// handle client queries to datastore
class ClientStore extends Datastore {

	private val clients = DB("clients")

	// get one client with details
	override def getOne(email: String):Option[DBObject] = {
		clients.findOne("email" $eq email) match {
			case Some(x) => Some(x)
			case _ => None
		}
	}

	// get all clients
	override def getAll():List[DBObject] = {
		// TODO
		List()
	}
}

// handle message queries to datastore
class MessageStore(email: String) extends Datastore {

	private val messages = DB("messages")

	// get the most recent message from a client e to this client instance 
	override def getOne(e: String):Option[DBObject] = {
		//TODO
		None
	}

	// get All messages
	override def getAll():List[DBObject] = {
		//TODO
		List()
	}
}