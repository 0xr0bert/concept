package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBelief

/** The specification for a [[Json]] file representing relationships between
  * beliefs.
  *
  * @param belief1Uuid
  *   The UUID of the first [[BasicBelief]].
  * @param belief2UUID
  *   The UUID of the second [[BasicBelief]].
  * @param value
  *   The value of the relationship. This defines how compatible b2 is given
  *   that you hold b1.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class RelationshipSpec(
    belief1Uuid: UUID,
    belief2Uuid: UUID,
    value: Double
)

/** This contains a [[Format]] for [[RelationshipSpec]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
object RelationshipSpec {

  /** This allows the data to be read from a JSON file.
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  implicit val relationshipSpecFormat: Format[RelationshipSpec] =
    Json.format[RelationshipSpec]
}
