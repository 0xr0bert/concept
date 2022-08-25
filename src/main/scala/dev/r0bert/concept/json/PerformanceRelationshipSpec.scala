package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBehaviour

/** The specification for a [[Json]] file representing how [[Belief]]s influence
  * the probability of performing a [[Behaviour]].
  *
  * @param beliefUuid
  *   The UUID of the [[Belief]].
  * @param behaviourUuid
  *   The UUID of the [[Behaviour]].
  * @param value
  *   The value of the relationship.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class PerformanceRelationshipSpec(
    behaviourUuid: UUID,
    beliefUuid: UUID,
    value: Double
)

/** This contains a [[Format]] for [[PerformanceRelationshipSpec]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
object PerformanceRelationshipSpec {

  /** This allows the data to be read from a JSON file.
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  implicit val performanceRelationshipSpecFormat
      : Format[PerformanceRelationshipSpec] =
    Json.format[PerformanceRelationshipSpec]
}
