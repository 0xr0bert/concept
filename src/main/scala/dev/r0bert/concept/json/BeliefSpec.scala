package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBelief

/** The specification for a [[Json]] file representing beliefs.
  *
  * @param name
  *   The name of the belief.
  * @param uuid
  *   The UUID of the belief, default uses [[UUID.randomUUID]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class BeliefSpec(
    name: String,
    uuid: UUID = UUID.randomUUID()
) {

  /** Convert this [[BeliefSpec]] into a [[BasicBelief]]
    *
    * @return
    *   the created [[BasicBelief]]
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def toBasicBelief: BasicBelief = BasicBelief(name, uuid)
}

/** This contains a [[Format]] for [[BeliefSpec]]
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
object BeliefSpec {

  /** This allows the data to be read from a JSON file, using default values if
    * not supplied.
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  implicit val beliefSpecFormat: Format[BeliefSpec] =
    Json.using[Json.WithDefaultValues].format[BeliefSpec]
}
