package controllers

import models.{Name, Person}
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

/**
 * Created by k-urano on 2015/09/04.
 */
class HandsOnController extends Controller {

  implicit val nameFormatter: Reads[Name] = (
    (JsPath \ "first").read[String](minLength[String](1)) and
      (JsPath \ "last").read[String](minLength[String](1))
    )(Name.apply _)

  implicit val personFormatter: Reads[Person] = (
    (JsPath \ "name").read[Name] and
      (JsPath \ "age").read[Int](min(0) keepAnd max(100))
    )(Person.apply _)

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

}
