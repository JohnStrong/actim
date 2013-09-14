package chatclient.test

import org.junit.runner.RunWith

import org.scalatest._
import org.scalatest.junit.JUnitRunner

import akka.actor.Actor
import akka.actor.Props

import xml._
import com.mongodb.casbah.Imports._

import chatclient.sink.Validate

// encapsulate constants in object
object ApplicationWorkflowConst {
	val PORT = 8080

	val ADDRESS = "127.0.0.1"
	
	val EMAIL = "strngj411@gmail.com"
}

// Actors used to communicate with the remote
object Test {
	case class Login(email: String)
}

class Tester extends Actor {
	def receive = {
		case Test.Login(email) => email
	}
}

// begin test class
@RunWith(classOf[JUnitRunner])
class ApplicationWorkflowTest extends FunSuite with BeforeAndAfter {

	before {
		
	}

	test("successful login results in a query returning 'name', 'about', 'friends'") {
		new Workflow(ApplicationWorkflowConst.EMAIL)
	}
}

class Workflow(email: String) extends Actor {

	override def preStart():Unit = {
		val tester = context.actorOf(Props[Tester], "tester")

		tester ! Test.Login
	}
}