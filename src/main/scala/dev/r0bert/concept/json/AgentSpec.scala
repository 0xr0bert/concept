package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format
import dev.r0bert.beliefspread.core.Agent
import dev.r0bert.beliefspread.core.BasicAgent
import dev.r0bert.beliefspread.core.Behaviour
import dev.r0bert.beliefspread.core.Belief

/** The specification for a [[Json]] file representing [[Agent]]s.
  *
  * @param uuid
  *   The UUID of the agent, default uses [[UUID.randomUUID]].
  * @param actions
  *   The [[Behaviour]]s the agent has performed, indexed by time.
  * @param activations
  *   The activation of the agent towards a [[Belief]] at a given time.
  * @param deltas
  *   The delta of the agent towards a [[Belief]].
  * @param friends
  *   The friends of the agent, with the weight of the friendship.
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
final case class AgentSpec(
    uuid: UUID = UUID.randomUUID(),
    actions: Map[Int, UUID] = Map.empty,
    activations: Map[Int, Map[UUID, Double]] = Map.empty,
    deltas: Map[UUID, Double] = Map.empty,
    friends: Map[UUID, Double] = Map.empty
) {

  /** Convert this [[AgentSpec]] to a [[Agent]].
    *
    * @param behaviours
    *   The behaviours known to the system.
    * @param beliefs
    *   The beliefs known to the system.
    * @return
    *   The [[Agent]].
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def toBasicAgent(
      behaviours: Iterable[Behaviour] = Array.empty[Behaviour],
      beliefs: Iterable[Belief] = Array.empty[Belief]
  ): BasicAgent =
    val a = BasicAgent(uuid)
    val uuidBehaviours = behaviours
      .map(b => (b.uuid, b))
      .toMap
    actions
      .foreach((time, b) => a.setAction(time, Some(uuidBehaviours(b))))

    val uuidBeliefs = beliefs
      .map(b => (b.uuid, b))
      .toMap
    activations
      .foreach((time, acts) =>
        acts.foreach((b, v) => a.setActivation(time, uuidBeliefs(b), Some(v)))
      )

    deltas
      .foreach((u, v) => a.setDelta(uuidBeliefs(u), Some(v)))

    a

  /** Link this agent to the other [[Agent]]s.
    *
    * @param agents
    *   The agents known to the system, mapped to their [[UUID]]s.
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def linkFriends(
      agents: Map[UUID, Agent] = Map.empty
  ): Unit =
    val thisAgent = agents(uuid)
    friends.map((a, v) => thisAgent.setFriendWeight(agents(a), Some(v)))
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

  /** Create a new [[AgentSpec]] from an [[Agent]].
    *
    * @param agent
    *   The [[Agent]].
    * @author
    *   Robert Greener
    * @since v0.0.1
    */
  def fromAgent(agent: Agent) =
    AgentSpec(
      agent.uuid,
      agent.getActions.map((k, v) => (k, v.uuid)).toMap,
      agent.getActivations
        .map((k1, v1) => (k1, v1.map((k2, v2) => (k2.uuid, v2)).toMap))
        .toMap,
      agent.getDeltas.map((k, v) => (k.uuid, v)).toMap,
      agent.getFriends().map((k, v) => (k.uuid, v)).toMap
    )
}
