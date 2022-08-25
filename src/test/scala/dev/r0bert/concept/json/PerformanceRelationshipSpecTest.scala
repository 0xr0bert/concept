package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.JsValue
import play.api.libs.json.Json

class PerformanceRelationshipSpecTest extends munit.FunSuite {
  test("valid") {
    val beliefUuid = UUID.randomUUID()
    val behaviourUuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
        "beliefUuid": "$beliefUuid",
        "behaviourUuid": "$behaviourUuid",
        "value": -0.4
    }
    """)
    val s = json.validate[PerformanceRelationshipSpec]
    assert(s.isSuccess, "JSON read unsuccessful")
    assertEquals(s.get.beliefUuid, beliefUuid)
    assertEquals(s.get.behaviourUuid, behaviourUuid)
    assertEquals(s.get.value, -0.4)
  }

  test("invalid beliefUuid") {
    val behaviourUuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
        "beliefUuid": "iasdasd",
        "behaviourUuid": "$behaviourUuid",
        "value": -0.4
    }
    """)
    val s = json.validate[PerformanceRelationshipSpec]
    assert(!s.isSuccess, "JSON read successful")
  }

  test("invalid behaviourUuid") {
    val beliefUuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
        "beliefUuid": "$beliefUuid",
        "behaviourUuid": "dsfs",
        "value": -0.4
    }
    """)
    val s = json.validate[PerformanceRelationshipSpec]
    assert(!s.isSuccess, "JSON read successful")
  }

  test("invalid value") {
    val behaviourUuid = UUID.randomUUID()
    val beliefUuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
        "beliefUuid": "$beliefUuid",
        "behaviourUuid": "$behaviourUuid",
        "value": "test"
    }
    """)
    val s = json.validate[PerformanceRelationshipSpec]
    assert(!s.isSuccess, "JSON read successful")
  }
}
