package chatclient.store

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import com.mongodb.casbah.Imports._

class Datastore() {

	// open chat client datastore
	private val DB = MongoClient()("instant_messenger")

	// client collection
	private lazy val clients = DB("users")

	// message collection
	private lazy val messages = DB("messages")

	// class that maps query tuples to a type
	private implicit def as(obj:DBObject) = new As(obj)

	// open a mongo db conneciton
	def find(email:String):Option[DBObject] = {
		clients.findOne("email" $eq email) match {
			case Some(x) => Some(x)
			case _ => None
		}
	}
}