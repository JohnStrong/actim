package chatclient.source

import akka.actor.Actor
import akka.actor.Props

import xml._

object Package {
	case class Login(email: String)
}

class Package extends Actor {
	
	def receive = {

		case Package.Login(email) => {
			sender ! <client><login>{email}</login></client>
		}
	}
	
}