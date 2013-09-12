package chatclient.source

import swing._
import swing.event._

object Client extends SimpleSwingApplication{
	// handle raw messages to the client message handler
	private val messgaeHandle = new ClientMessageHandler

	// Swing initalizer
	def top = mainFrame(500, 500)

	/*******************************************
	* 
	* Private classes to build the User Interface
	*
	********************************************/
	private def mainFrame(w:Int, h:Int):Frame = {
		new MainFrame {
			title = "Scala Instant Messenger"
			contents = new GridPanel(2,2) {
				contents += new Button {
					text = "login"
					reactions += {
						case ButtonClicked(_) => {
							messgaeHandle.login("strngj411@gmail.com")
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