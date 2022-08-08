package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.JsValue

class RelationshipSpecTest extends munit.FunSuite {
  test("All valid") {
    val u1 = UUID.randomUUID()
    val u2 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "belief1Uuid": "$u1",
            "belief2Uuid": "$u2",
            "value": 0.1
        }
    """)
    val b = json.validate[RelationshipSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.belief1Uuid, u1)
    assertEquals(b.get.belief2Uuid, u2)
    assertEqualsDouble(b.get.value, 0.1, 0.001)
  }

  test("Invalid uuid1") {
    val u2 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "belief1Uuid": "aaa",
            "belief2Uuid": "$u2",
            "value": 0.1
        }
    """)
    val b = json.validate[RelationshipSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid uuid2") {
    val u1 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "belief1Uuid": "$u1",
            "belief2Uuid": "aaa",
            "value": 0.1
        }
    """)
    val b = json.validate[RelationshipSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid value") {
    val u1 = UUID.randomUUID()
    val u2 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
        {
            "belief1Uuid": "$u1",
            "belief2Uuid": "$u2",
            "value": "a"
        }
    """)
    val b = json.validate[RelationshipSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Array of valid RelationshipSpecs") {
    val u1 = UUID.randomUUID()
    val u2 = UUID.randomUUID()
    val u3 = UUID.randomUUID()
    val u4 = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
    [
        {
            "belief1Uuid": "$u1",
            "belief2Uuid": "$u2",
            "value": 0.1
        }, {
            "belief1Uuid": "$u3",
            "belief2Uuid": "$u4",
            "value": -0.1
        }
    ]
    """)
    val b = json.validate[Array[RelationshipSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).belief1Uuid, u1)
    assertEquals(b.get(0).belief2Uuid, u2)
    assertEqualsDouble(b.get(0).value, 0.1, 0.001)
    assertEquals(b.get(1).belief1Uuid, u3)
    assertEquals(b.get(1).belief2Uuid, u4)
    assertEqualsDouble(b.get(1).value, -0.1, 0.001)
  }
}
