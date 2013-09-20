package chatclient.source

import akka.actor.Actor
import akka.actor.Props

/**
* The purpose of this class is to take in raw information about an event and package it into a 
* format expected by the remote actor
**/
object Package {
	
	case class PackageLogin(email: String)

	class Package extends Actor {

		import xml._
		import RemoteHandler._
		
		def receive = {

			// package a login request from a client
			case PackageLogin(email) => {
				sender ! PackagedLogin(<client><login>{email}</login></client>)
			}
		}
	
	}
}