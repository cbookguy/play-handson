import org.junit.Ignore
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Created by k-urano on 2015/09/04.
 */
@RunWith(classOf[JUnitRunner])
class HandsonControllerSpec extends Specification{

//  "HandsonController#normal" should {
//
//    "send 400 on a bad request" in new WithApplication{
//      route(FakeRequest(POST, "/v1/normal")) must beSome.which (status(_) == BAD_REQUEST)
//    }
//
//    "response person" in new WithApplication{
//      val person = route(FakeRequest(POST, "/v1/normal")).post("{ \"name\": {\"first\": \"urano\", \"last\": \"katsuyoshi\"}, \"age\": 37}")
//      status(person) must equalTo(OK)
//      contentType(person) must beSome.which(_ == "application/json")
//      contentAsString(person) must contain ("OK")
//    }
//  }
}
