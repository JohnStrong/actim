package chatclient.store

import xml._

import com.mongodb.casbah.Imports._

// wrapper class for calls the datastore object,
// building an xml result from the query,
// returns to the Remote
class QueryProcessor {

	// initalize a new mongodb datastore object
	private def datastore = new Datastore

	// find client information to build profile view
	def getProfile(implicit email:String):Elem = {
		datastore.getClientProfile(email) match {
			case Some(x) =>  {
				x.productIterator.toList foreach( x => {
					println(x) 
				})

				<example>"test success!"</example>
			}
			case _ => <example>"test failure!"</example>
		}
	}

	// get any offline messages stored for the client
	def getUnreadMessages(implicit email: String):Elem = {
		datastore.getOfflineMessages(email)

		<example>"test"</example>
	}
}

// Mongo store
class Datastore() {

	// class that maps query tuples to a type
	private implicit def as(obj:DBObject) = new As(obj)

	// open chat client datastore
	private val DB = MongoClient()("instant_messenger")

	// client collection
	private def clients = DB("users")

	// message collection
	private def messages = DB("messages")

	def getClientProfile(email: String):Option[Tuple3[String, String, List[String]]] = {
		clients.findOne("email" $eq email) match {
			case Some(x) => {
				Some((x string("name"), x string("about"), x list("friends")))
			}
			case _ => {
				None
			}
		}
	}

	def getOfflineMessages(email: String) {
		//val a = List() ++ for (m <- messages.find("email" $ eq email)) yield x
		//println(a)
		
	}	
}