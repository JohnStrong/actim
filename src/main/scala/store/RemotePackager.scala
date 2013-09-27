package chatclient.store

import akka.actor.Actor
import akka.actor.Actor._
import akka.actor.Props

import xml._
import com.mongodb.casbah.Imports._

class RemotePackager {

	import ClientEntity._

	implicit def as(obj: DBObject) = new As(obj)
	
	def profile(entity: DBObject):Unit = {
		// packaging message response of profile data to the client
	}
}