package chatclient.source

import swing._
import swing.event._

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

import Client._

object ClientInterface extends SimpleSwingApplication{

	// handle raw messages to the client message handler
	val system = ActorSystem("ClientSystem")
	val clientActor = system.actorOf(Props[Client], name = "clientHandler.source.chatclient")

	// Swing initalizer
	def top = mainFrame(500, 500)

	// build the user interface
	def mainFrame(w:Int, h:Int):Frame = {
		new MainFrame {
			title = "Scala Instant Messenger"
			contents = new GridPanel(2,2) {
				contents += new Button {
					text = "login"
					reactions += {
						case ButtonClicked(_) => {
							clientActor ! Login("strngj411@gmail.com")
						}
					}
				}
				contents += new Button {
					text = "friends"
					reactions += {
						case ButtonClicked(_) => {
							
						}
					}
				}
			}

			size = new Dimension(w, h)
		}
	}
}