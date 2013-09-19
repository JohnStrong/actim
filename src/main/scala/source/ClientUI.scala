package chatclient.source

import swing._
import swing.event._

import Client._

object ClientInterface extends SimpleSwingApplication{

	// handle raw messages to the client message handler
	val client = new Client

	// Swing initalizer
	def top = mainFrame(420, 120)

	// build the user interface
	def mainFrame(w:Int, h:Int):Frame = {
		new MainFrame {
			title = "Scala Instant Messenger"
			contents = new FlowPanel {
				contents += new TextField {
					
					editable = true
					text = "example@gmail.com"

					reactions += {
						case e:KeyTyped => {
							println(e)
						}
					}
				}

				contents += new Button {

					text = "login"

					reactions += {
						case ButtonClicked(_) => {
							// todo
						}
					}
				}

			}

			size = new Dimension(w, h)
		}
	}
}