package controllers

import java.util.concurrent.TimeUnit

import play.api.libs.json.{JsValue, JsPath, Json}
import play.api.libs.ws.{WS, WSResponse}
import scala.concurrent.ExecutionContext.Implicits._
import play.api.mvc._
import play.api.Play.current
import scala.concurrent.{Await, Future}

class FutureFeatureController extends Controller {

  final val endpoint = "http://api.openweathermap.org/data/2.5/weather"

  def weather = Action.async {
      val f1: Future[WSResponse] = WS.url(endpoint + "?q=Tokyo,jp").get()
      val f2: Future[WSResponse] = WS.url(endpoint + "?q=Osaka,jp").get()
      val f3: Future[WSResponse] = WS.url(endpoint + "?q=Fukuoka-shi,jp").get()

    (for {
        r1 <- f1
        r2 <- f2
        r3 <- f3
      } yield Ok(Json.obj(
        "?q=Tokyo,jp" -> (getName(r1)).head,
        "?q=Osaka,jp" -> (getName(r2)).head,
        "?q=Fukuoka-shi,jp" -> (getName(r3)).head)
      ))
    .recover {
      case e: Exception => InternalServerError(Json.obj("status" -> "InternalServerError"))
    }
  }

  def getName(r: WSResponse): List[JsValue] = {
    (JsPath \ "name")(r.json)
  }

  def getInfo(f: Future[WSResponse]): WSResponse = {
    Await.result(f, scala.concurrent.duration.Duration(1000, TimeUnit.MILLISECONDS))
  }

}
