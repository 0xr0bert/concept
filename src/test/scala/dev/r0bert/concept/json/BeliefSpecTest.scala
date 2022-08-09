package dev.r0bert.concept.json

import play.api.libs.json.Json
import play.api.libs.json.JsValue
import java.util.UUID
import dev.r0bert.beliefspread.core.BasicBelief

class BeliefSpecTest extends munit.FunSuite {
  test("Valid name and UUID no perceptions") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "uuid": "$uStr"
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertEquals(b.get.uuid, u)
  }

  test("Valid name and unspecified UUID no perceptions") {
    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1"
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertNotEquals(
      b.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
  }

  test("Invalid name and unspecified UUID no perceptions") {
    val json: JsValue = Json.parse(s"""
    {
      "name": 2
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid name and valid UUID no perceptions") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "uuid": "$uStr"
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Valid name and invalid UUID no perceptions") {
    val json: JsValue = Json.parse("""
    {
      "name": "B",
      "uuid": "aaa"
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Array of valid BeliefSpecs no perceptions") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val json: JsValue = Json.parse(s"""
      [
        {
          "name": "B1"
        },
        {
          "name": "B2",
          "uuid": "$uStr"
        }
      ]
    """)
    val b = json.validate[Array[BeliefSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).name, "B1")
    assertEquals(b.get(1).name, "B2")
    assertEquals(b.get(1).uuid, u)
  }

  test("toBasicBelief works no perceptions") {
    val u = UUID.randomUUID()
    val bi: BeliefSpec = BeliefSpec("b1", u)
    val bo: BasicBelief = bi.toBasicBelief()

    assertEquals(bo.name, "b1")
    assertEquals(bo.uuid, u)
  }
}
