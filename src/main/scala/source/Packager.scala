package chatclient.source

import akka.actor.Actor
import akka.actor.Props

/**
* The purpose of this class is to take in raw information about an event and package it into a 
* format expected by the remote actor
**/
object Packager {
	
	// package in xml a client login
	case class PackageLogin(email: String)
	
	// package in xml a message send by the client
	case class PackageMessage(to: String, from: String, body: String)
	
	class Packager extends Actor {

		import xml._
		import Distributer._
		
		def receive = {

			case PackageLogin(email) => 
				sender ! PLogin(<client><login>{email}</login></client>)

			case PackageMessage(to, from, body) => {
				sender ! PMessage(
					<client>
						<message>
							<to>{to}</to>
							<from>{from}</from>
							<body>{body}</body>
						</message>
					</client>
				)
			}

		}
	
	}
}