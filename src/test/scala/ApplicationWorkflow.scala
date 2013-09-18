package chatclient.test.runners

import org.junit.runner.RunWith

import org.scalatest._
import org.scalatest.junit.JUnitRunner

import akka.actor.Actor
import akka.actor.Props

import xml._
import com.mongodb.casbah.Imports._

import chatclient.sink.Remote

// encapsulate constants in object
object ApplicationWorkflowConst {
	
	val PORT = 8080

	val ADDRESS = "127.0.0.1"
	
	val EMAIL = "strngj411@gmail.com"
}

// begin test class
@RunWith(classOf[JUnitRunner])
class ApplicationWorkflowTest extends FunSuite with BeforeAndAfter {

	before {
		// todo
	}

	test("successful login results in a query returning 'name', 'about', 'friends'") {
		new Workflow(ApplicationWorkflowConst.EMAIL)
	}
}