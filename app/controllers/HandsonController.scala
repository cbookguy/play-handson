package controllers

import models.Person
import play.api.libs.json._
import play.api.mvc._

/**
 * Created by k-urano on 2015/09/04.
 */
class HandsOnController extends Controller {

  import formatters.PersonFormatter._

  def normal = Action(BodyParsers.parse.json) { request =>
    val person = request.body.validate[Person]
    person.fold(
      errors => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors)))
      },
      person => {
        Ok(Json.obj("status" -> "OK", "message" -> ("Person name is '" + person.name.first + "' bind")))
      }
    )
  }

  def normal2 = Action(BodyParsers.parse.json) { request =>
    request.body.validate[Person] match {
      case s: JsSuccess[Person] => {
        val person: Person = s.get
        Ok(Json.obj("status" -> "OK", "message" -> ("Person name is '" + person.name.first + "' bind")))
      }
      case e: JsError => {
        BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(e)))
      }
    }
  }

  implicit val ageTransform = { age: Int => __.json.update((__ \ "age").json.put(JsNumber(age))) }

  def transform = Action(parse.json) { json =>
    val personVal = json.body
    val transVal = personVal.transform(ageTransform(18))
    personVal.validate[Person] match {
      case s: JsSuccess[Person] => {
        Ok(Json.obj("original" -> s.get, "transform" -> Json.toJson(transVal.get)))
      }
      case e: JsError => {
        BadRequest(Json.obj("message" -> JsError.toJson(e)))
      }
    }
  }

}
