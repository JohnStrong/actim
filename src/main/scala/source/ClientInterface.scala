package chatclient.source

import swing._
import swing.event._

/**
* This object creates the UI that a cient will interact with
* UI events will be passed to the backend as messages to be processed and evaluated
**/
object ClientInterface extends SimpleSwingApplication{

	val loginField = new TextField {
		text = "example@gmail.com"
	}

	val action = new Button {
		text = "login"
		reactions += {
			case ButtonClicked(_) => {
				Client.login("j.strong1@nuigalway.ie")
			}
		}
	}

	// Swing initalizer
	val mainView = new FlowPanel {
		contents += loginField
		contents += action
	}

	def top = {
		new MainFrame {

			title = "Akka Instant Messenger"

			contents = mainView

			size = new Dimension(400, 100)
		}
	}
}