package chatclient.source

import scala.swing._
import swing.event._

object Client extends SimpleSwingApplication{
	// handle raw messages to the client message handler
	private val messgaeHandle = new ClientHandler

	// Swing initalizer
	def top = mainFrame(400, 500)

	/*******************************************
	* 
	* Private classes to build the User Interface
	*
	********************************************/
	private def mainFrame(w:Int, h:Int):Frame = {
		new MainFrame {
				title = "Scala Instant Messenger"
				contents = new GridPanel(2,2) {
					contents += button("login")
					contents += button("friends")
				}
				size = new Dimension(w, h)
			}
	}

	private def button(t:String):Button = {
		new Button {
			text = t
			reactions += {
				case ButtonClicked(_) => {
					messgaeHandle.login("strngj411@gmail.com")
				}
			}
		}
	}
}