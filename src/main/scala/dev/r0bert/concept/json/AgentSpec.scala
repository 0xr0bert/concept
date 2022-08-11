package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.Agent
import dev.r0bert.beliefspread.core.BasicAgent
import dev.r0bert.beliefspread.core.Behaviour
import scala.collection.parallel.CollectionConverters._

/** The specification for a [[Json]] file representing [[Agent]]s.
  *
  * @param uuid
  *   The UUID of the agent, default uses [[UUID.randomUUID]].
  * @param actions
  *   The [[Behaviour]]s the agent has performed, indexed by time.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class AgentSpec(
    uuid: UUID = UUID.randomUUID(),
    actions: Map[Int, UUID] = Map.empty
) {

  /** Convert this [[AgentSpec]] to a [[Agent]].
    *
    * @param behaviours
    *   The behaviours known to the system.
    * @return
    *   The [[Agent]].
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def toBasicAgent(
      behaviours: Iterable[Behaviour] = Array.empty[Behaviour]
  ): BasicAgent =
    val a = BasicAgent(uuid)
    val uuidBehaviours = behaviours.par
      .map(b => (b.uuid, b))
      .toMap
    actions.foreach((time, b) => a.setAction(time, Some(uuidBehaviours(b))))
    a
}

/** This contains a [[Format]] for [[AgentSpec]]
  *
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
object AgentSpec {

  /** This allows the data to be read from a JSON file, using default values if
    * not supplied.
    *
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  implicit val agentSpecFormat: Format[AgentSpec] =
    Json.using[Json.WithDefaultValues].format[AgentSpec]
}
