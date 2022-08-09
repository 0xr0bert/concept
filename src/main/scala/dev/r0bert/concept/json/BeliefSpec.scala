package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.BasicBelief
import scala.collection.parallel.CollectionConverters._
import dev.r0bert.beliefspread.core.Behaviour
import dev.r0bert.beliefspread.core.Belief

/** The specification for a [[Json]] file representing beliefs.
  *
  * @param name
  *   The name of the belief.
  * @param uuid
  *   The UUID of the belief, default uses [[UUID.randomUUID]]
  * @param perceptions
  *   A mapping from behaviours (uuids) to perceptions. This defines how an
  *   agent performing the behaviour can be assumed to be driven by the belief.
  * @param relationships
  *   A mapping from beliefs (uuids) to relationships. This defines how
  *   compatible holding the other belief is given that you hold this belief.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class BeliefSpec(
    name: String,
    uuid: UUID = UUID.randomUUID(),
    perceptions: Map[UUID, Double] = Map(),
    relationships: Map[UUID, Double] = Map()
) {

  /** Convert this [[BeliefSpec]] into a [[BasicBelief]]
    *
    * @param behaviours
    *   The known [[Behaviour]]s in the system.
    * @return
    *   the created [[BasicBelief]]
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def toBasicBelief(
      behaviours: Iterable[Behaviour] = Array[Behaviour]()
  ): BasicBelief =
    val b = BasicBelief(name, uuid)
    behaviours.par
      .foreach(beh =>
        perceptions.get(beh.uuid) match {
          case Some(v) => b.setPerception(beh, Some(v))
          case None    =>
        }
      )
    b

  /** Link the relationships between [[Belief]]s
    *
    * @param beliefs
    *   The known [[Belief]]s in the system.
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def linkBeliefRelationships(beliefs: Iterable[Belief]): Unit =
    val uuidBeliefs = beliefs.par
      .map(b => (b.uuid, b))
      .toMap
    relationships.par.map((r, v) =>
      uuidBeliefs.get(r) match {
        case Some(b) => uuidBeliefs(uuid).setRelationship(b, Some(v))
        case None    =>
      }
    )

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
