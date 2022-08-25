package dev.r0bert.concept.performancerelationships

import dev.r0bert.concept.json.PerformanceRelationshipSpec
import java.util.UUID
import dev.r0bert.beliefspread.core.Belief
import dev.r0bert.beliefspread.core.Behaviour

/** This object contains the implicit class which turns arrays of
  * [[PerformanceRelationshipSpec]] into [[PerformanceRelationships]].
  *
  * @author
  *   Robert Greener
  * @since v0.0.1
  */
object PerformanceRelationshipUtils {
  implicit class PerformanceRelationshipSpecTools(
      val prss: Array[PerformanceRelationshipSpec]
  ) {
    def toPerformanceRelationships(
        beliefs: Map[UUID, Belief],
        behaviours: Map[UUID, Behaviour]
    ): PerformanceRelationships =
      prss
        .map(prs =>
          ((beliefs(prs.beliefUuid), behaviours(prs.behaviourUuid)), prs.value)
        )
        .toMap
  }
}
