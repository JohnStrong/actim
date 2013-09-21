package chatclient.store

import com.mongodb.casbah.Imports._

trait Datastore {

	// open chat client datastore
	protected val DB = MongoClient()("instant_messenger")

	def getOne(s: String):Option[DBObject]

	def getAll():List[DBObject]

	def insert(entry:DBObject):Unit

	// add entry to a colleciton
	def update(entry:DBObject):Unit

	// delete entry in a collection
	def remove(entry:DBObject):Unit

	// drop collection
	def drop():Unit
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
	override def getAll():List[DBObject] =
		clients.find().asInstanceOf[List[DBObject]]

	override def insert(e:DBObject):Unit = clients.insert(e)

	// add a new client to the collection
	override def update(e:DBObject):Unit = clients.save(e)

	override def remove(e:DBObject):Unit = clients.remove(e)

	override def drop():Unit = clients.drop()

}

// handle message queries to datastore
class MessageStore(email: String) extends Datastore {

	private val messages = DB("messages")

	// get the most recent message from a client e to this client instance 
	override def getOne(message: String):Option[DBObject] = {
		messages.findOne("mid" $eq message) match {
			case Some(x) => Some(x)
			case _ => None
		}
	}

	// get All messages
	override def getAll():List[DBObject] = 
		messages.find("to" $eq email).asInstanceOf[List[DBObject]]

	// add a new message to the collection
	override def insert(e:DBObject):Unit = messages.insert(e)

	override def update(e:DBObject):Unit = messages.save(e)

	override def remove(e:DBObject):Unit = messages.remove(e)

	override def drop():Unit = messages.drop()

}