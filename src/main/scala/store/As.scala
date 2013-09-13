package chatclient.store

import com.mongodb.casbah.Imports._

class As(obj: DBObject) {

	// return field from mongo collection as an integer
	def number(field: String):Int = {
		obj.getOrElse(field, throw new Exception("query number exception") ).asInstanceOf[Int]
	}

	// return field from mongo collection as a string
	def string(field: String):String = {
		obj.getOrElse(field, throw new Exception("query string exception") ).asInstanceOf[String]
	}

	// return field from mongo collection as a list
	def list(field: String):List[String] = {
		(List() ++ obj(field).asInstanceOf[BasicDBList]) map {
				_.asInstanceOf[Any].asInstanceOf[DBObject]
					.getOrElse("email", throw new Exception("query list exception")).asInstanceOf[String]
		}
	}
}