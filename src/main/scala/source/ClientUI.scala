package chatclient.source

import swing._
import swing.event._

import Client._

/**
* This object creates the UI that a cient will interact with
* UI events will be passed to the backend as messages to be processed and evaluated
**/
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
							//todo
						}
					}
				}

				contents += new Button {

					text = "login"

					reactions += {
						case ButtonClicked(_) => {
							// todo
							client.login("strngj411@gmail.com")
						}
					}
				}

			}

			size = new Dimension(w, h)
		}
	}
}