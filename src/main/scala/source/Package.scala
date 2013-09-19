package chatclient.source

import akka.actor.Actor
import akka.actor.Props

object Package {
	
	case class PackageLogin(email: String)

	class Package extends Actor {

		import xml._
		import RemoteLookup._
		
		def receive = {

			// package a login request from the client
			case PackageLogin(email) => {
				// sender ! Client.PackagedLogin(<client><login>{email}</login></client>)
			}
		}
	
	}
}