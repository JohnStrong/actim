package chatclient.store

import com.mongodb.casbah.Imports._

// wrapper class for calls the datastore object,
// building an xml result from the query,
// returns to the Remote
class QueryProcessor {

	// class that maps query tuples to a type
	private implicit def as(obj:DBObject) = new As(obj)

	// initalize a new mongodb datastore object
	private def datastore = new Datastore

	// find client information to build profile view
	def find(email:String):String = {
		val entry = datastore.findClient(email) match {
			case Some(x) => "test"
			case _ => "test fail"
		}

		entry
	}
}

// Mongo store
class Datastore() {

	// open chat client datastore
	private val DB = MongoClient()("instant_messenger")

	// client collection
	private lazy val clients = DB("users")

	// message collection
	private lazy val messages = DB("messages")

	def findClient(email: String):Option[DBObject] = {
		clients.findOne("email" $eq email)
	}
}