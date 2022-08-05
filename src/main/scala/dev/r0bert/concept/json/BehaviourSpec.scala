package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBehaviour

final case class BehaviourSpec(
    name: String,
    uuid: UUID = UUID.randomUUID()
) {

  /** Convert this [[BehaviourSpec]] into a [[BasicBehaviour]]
    *
    * @return
    *   the created [[BasicBehaviour]]
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def toBasicBehaviour: BasicBehaviour = BasicBehaviour(name, uuid)
}
object BehaviourSpec {
  implicit val behaviourSpecFormat: Format[BehaviourSpec] =
    Json.using[Json.WithDefaultValues].format[BehaviourSpec]
}
