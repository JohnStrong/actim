package chatclient.store

import com.mongodb.casbah.Imports._

trait Datastore {

	// open chat client datastore
	protected val DB = MongoClient()("instant_messenger")

	def find():Iterator[DBObject]

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

	
	override def find():Iterator[DBObject] = clients.find()

	override def insert(e:DBObject):Unit = clients.insert(e)

	// add a new client to the collection
	override def update(e:DBObject):Unit = clients.save(e)

	override def remove(e:DBObject):Unit = clients.remove(e)

	override def drop():Unit = clients.drop()

}

// handle message queries to datastore
class MessageStore extends Datastore {

	private val messages = DB("messages")

	override def find():Iterator[DBObject] = messages.find()

	// add a new message to the collection
	override def insert(e:DBObject):Unit = messages.insert(e)

	override def update(e:DBObject):Unit = messages.save(e)

	override def remove(e:DBObject):Unit = messages.remove(e)

	override def drop():Unit = messages.drop()

}