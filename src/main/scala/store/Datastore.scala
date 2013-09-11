package chatclient.store

import scala.actors.Actor
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.remote.Node

import com.mongodb.casbah.Imports._

class Datastore() {
	// open chat client datastore
	private val DB = MongoClient()("instant_messenger")

	// open collections
	private lazy val CLIENTS = DB("users")
	private lazy val MESSAGES = DB("messages")

	private implicit def as(obj:DBObject) = new As(obj)

	// open a mongo db conneciton
	def find(email:String):Option[DBObject] = {
		CLIENTS.findOne("email" $eq email) match {
			case Some(x) => Some(x)
			case _ => None
		}
	}
}