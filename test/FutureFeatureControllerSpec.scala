import org.specs2.mutable.Specification
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

class FutureFeatureControllerSpec extends Specification {

  "FutureFeatureController#weather response" should {
    "contains (Tokyo,Osaka,Fukuoka)" in new WithApplication {
      val Some(result) = route(FakeRequest(GET, "/v1/future/weather"))
      contentAsString(result) must contain("Tokyo")
      contentAsString(result) must contain("Osaka")
      contentAsString(result) must contain("Fukuoka")
    }
  }
}
