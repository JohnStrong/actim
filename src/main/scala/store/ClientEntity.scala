package chatclient.store

import com.mongodb.casbah.Imports._

import xml._

object Package {

	// class that maps query tuples to a type
	implicit def as(obj: DBObject) = new As(obj)

	// package client colums
	def packageClient(entity: DBObject):Elem = {
		// get attributes we are interested in
		val email = entity string("email")
		val name = entity string("name")
		val about = entity string("about")

		<client>
			<id>{email}</id>
			<name>{name}</name>
			<about>{about}</about>
		</client>
	}

	// package message columns
	def packageMessage(entity: DBObject):Elem =  {
		<message>test</message>
	}
}

// wrapper class for calls the datastore object
class ClientEntity(datastore: Datastore) {
	
	def getAll():Unit = {
		datastore.getAll()
	}

	// find client information to build profile view
	def getOne(implicit email: String):Option[Elem] = {

		datastore.getOne(email: String) match {

			case Some(x) => Some(Package.packageClient(x))
			case _ => None
		}
	}
}