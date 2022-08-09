package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.JsValue

class PerceptionSpecTest extends munit.FunSuite {
  test("All valid") {
    val u1 = UUID.randomUUID()
    val u2 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "beliefUuid": "$u1",
            "behaviourUuid": "$u2",
            "value": 0.1
        }
    """)
    val b = json.validate[PerceptionSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.beliefUuid, u1)
    assertEquals(b.get.behaviourUuid, u2)
    assertEqualsDouble(b.get.value, 0.1, 0.001)
  }

  test("Invalid uuid1") {
    val u2 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "beliefUuid": "aaa",
            "behaviourUuid": "$u2",
            "value": 0.1
        }
    """)
    val b = json.validate[PerceptionSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid uuid2") {
    val u1 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "beliefUuid": "$u1",
            "behaviourUuid": "aaa",
            "value": 0.1
        }
    """)
    val b = json.validate[PerceptionSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid value") {
    val u1 = UUID.randomUUID()
    val u2 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "beliefUuid": "$u1",
            "behaviourUuid": "$u2",
            "value": "a"
        }
    """)
    val b = json.validate[PerceptionSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Array of valid PerceptionSpecs") {
    val u1 = UUID.randomUUID()
    val u2 = UUID.randomUUID()
    val u3 = UUID.randomUUID()
    val u4 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
    [
        {
            "beliefUuid": "$u1",
            "behaviourUuid": "$u2",
            "value": 0.1
        }, {
            "beliefUuid": "$u3",
            "behaviourUuid": "$u4",
            "value": -0.1
        }
    ]
    """)
    val b = json.validate[Array[PerceptionSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).beliefUuid, u1)
    assertEquals(b.get(0).behaviourUuid, u2)
    assertEqualsDouble(b.get(0).value, 0.1, 0.001)
    assertEquals(b.get(1).beliefUuid, u3)
    assertEquals(b.get(1).behaviourUuid, u4)
    assertEqualsDouble(b.get(1).value, -0.1, 0.001)
  }
}
