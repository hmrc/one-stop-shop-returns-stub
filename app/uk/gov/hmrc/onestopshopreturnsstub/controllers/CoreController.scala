package uk.gov.hmrc.onestopshopreturnsstub.controllers

import play.api.libs.json.JsValue
import play.api.mvc._
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.time.Clock
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class CoreController  @Inject()(
                                 cc: ControllerComponents,
                                 clock: Clock
                               )
  extends BackendController(cc) {

  implicit val ec: ExecutionContext = cc.executionContext

  def submitVatReturn(): Action[AnyContent] = Action.async { implicit request =>
    val jsonBody: Option[JsValue] = request.body.asJson

    JsonSchemaHelper.applySchemaHeaderValidation(request.headers) {
      JsonSchemaHelper.applySchemaValidation("/resources/schemas/core_return.json", jsonBody) {
        Future.successful(Accepted(""))
      }
    }
  }

}
