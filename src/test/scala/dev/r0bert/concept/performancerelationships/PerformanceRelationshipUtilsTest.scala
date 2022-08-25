package dev.r0bert.concept.performancerelationships

import dev.r0bert.concept.json.PerformanceRelationshipSpec
import dev.r0bert.concept.performancerelationships.PerformanceRelationshipUtils.PerformanceRelationshipSpecTools
import dev.r0bert.beliefspread.core.BasicBelief
import dev.r0bert.beliefspread.core.BasicBehaviour
import scala.util.Random

class PerformanceRelationshipUtilsTest extends munit.FunSuite {
  test("conversion on empty array") {
    val a: Array[PerformanceRelationshipSpec] = Array.empty

    val prs = a.toPerformanceRelationships(Map.empty, Map.empty)
    assert(prs.isEmpty, "prs should be empty!")
  }

  test("conversion of non-empty") {
    val beliefs = (1 to 100)
      .map(b => BasicBelief(s"b$b"))
      .map(b => (b.uuid, b))
      .toMap
    val behaviours = (1 to 100)
      .map(b => BasicBehaviour(s"b$b"))
      .map(b => (b.uuid, b))
      .toMap

    val random = new Random
    val prss = (0 to 99)
      .map(i =>
        PerformanceRelationshipSpec(
          behaviours.values.toArray.apply(i).uuid,
          beliefs.values.toArray.apply(random.nextInt(beliefs.size)).uuid,
          random.nextDouble() * 2 - 1
        )
      )
      .toArray

    val prs = prss.toPerformanceRelationships(beliefs, behaviours)
    assertEquals(prs.size, 100)
    prss.foreach(p =>
      assertEquals(
        prs((beliefs(p.beliefUuid), behaviours(p.behaviourUuid))),
        p.value
      )
    )
  }
}
