package dev.r0bert.concept.json

import play.api.libs.json.Json
import play.api.libs.json.JsValue
import java.util.UUID

class BehaviourSpecTest extends munit.FunSuite {
  test("Valid name and UUID") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val json: JsValue = Json.parse(s"""
    {
      "name": "Behaviour 1",
      "uuid": "$uStr"
    }
    """)
    val b = json.validate[BehaviourSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Behaviour 1")
    assertEquals(b.get.uuid, u)
  }

  test("Valid name and unspecified UUID") {
    val json: JsValue = Json.parse(s"""
    {
      "name": "Behaviour 1"
    }
    """)
    val b = json.validate[BehaviourSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Behaviour 1")
    assertNotEquals(
      b.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
  }

  test("Invalid name and unspecified UUID") {
    val json: JsValue = Json.parse(s"""
    {
      "name": 2
    }
    """)
    val b = json.validate[BehaviourSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid name and valid UUID") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "uuid": "$uStr"
    }
    """)
    val b = json.validate[BehaviourSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Valid name and invalid UUID") {
    val json: JsValue = Json.parse("""
    {
      "name": "B",
      "uuid": "aaa"
    }
    """)
    val b = json.validate[BehaviourSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Array of valid BehaviourSpecs") {
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
    val b = json.validate[Array[BehaviourSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).name, "B1")
    assertEquals(b.get(1).name, "B2")
    assertEquals(b.get(1).uuid, u)
  }
}
