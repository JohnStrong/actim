package chatclient.source

import swing._
import swing.event._

/**
* This object creates the UI that a cient will interact with
* UI events will be passed to the backend as messages to be processed and evaluated
**/
object ClientInterface extends SimpleSwingApplication{

	// Swing initalizer
	def top = {
		new MainFrame {
			title = "Akka Instant Messenger"
			contents = new FlowPanel {
				contents += new TextField {
					
					editable = true
					text = "john_doe@example.com"

					reactions += {
						case e:KeyTyped => {
							// TODO:
						}
					}
				}

				contents += new Button {
					text = "login"
					reactions += {
						case ButtonClicked(_) => {

							//login with text from TextField component
							Client.login("j.strong1@nuigalway.ie")
						}
					}
				}

			}

			size = new Dimension(400, 100)
		}
	}
}