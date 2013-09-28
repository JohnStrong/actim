package chatclient.source

import swing._
import swing.event._

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
	private def mainFrame(w:Int, h:Int):Frame = {
		new MainFrame {
			title = "Scala Instant Messenger"
			contents = new FlowPanel {
				contents += new TextField {
					
					editable = true
					text = "john_doe@example.com"

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
							//login with text from TextField component
							client.login("j.strong1@nuigalway.ie")
						}
					}
				}

			}

			size = new Dimension(w, h)
		}
	}
}