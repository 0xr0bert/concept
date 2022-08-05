package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBehaviour

/** The specification for a [[Json]] file representing behaviours.
  *
  * @param name
  *   The name of the behaviour.
  * @param uuid
  *   The UUID of the behaviour, default uses [[UUID.randomUUID]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
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

  /** This allows the data to be read from a JSON file, using default values if
    * not supplied.
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  implicit val behaviourSpecFormat: Format[BehaviourSpec] =
    Json.using[Json.WithDefaultValues].format[BehaviourSpec]
}
