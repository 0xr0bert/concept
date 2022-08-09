package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBelief

/** The specification for a [[Json]] file representing perceptions between
  * beliefs and behaviours..
  *
  * @param beliefUuid
  *   The UUID of the [[BasicBelief]].
  * @param behaviourUUID
  *   The UUID of the [[BasicBeheaviour]].
  * @param value
  *   The value of the perception. This defines how an agent performing the
  *   behaviour can be assumed to be driven by the belief.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class PerceptionSpec(
    beliefUuid: UUID,
    behaviourUuid: UUID,
    value: Double
)

/** This contains a [[Format]] for [[PerceptionSpec]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
object PerceptionSpec {

  /** This allows the data to be read from a JSON file.
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  implicit val perceptionSpecFormat: Format[PerceptionSpec] =
    Json.format[PerceptionSpec]
}
