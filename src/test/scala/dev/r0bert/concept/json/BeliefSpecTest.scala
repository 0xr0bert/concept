package dev.r0bert.concept.json

import play.api.libs.json.Json
import play.api.libs.json.JsValue
import java.util.UUID
import dev.r0bert.beliefspread.core.BasicBelief
import dev.r0bert.beliefspread.core.BasicBehaviour

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

  test("Valid name and UUID no perceptions and relationships") {
    val u = UUID.randomUUID()
    val uStr = u.toString()

    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "uuid": "$uStr",
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertEquals(b.get.uuid, u)
    assertEqualsDouble(b.get.relationships(r1uuid), 0.3, 0.001)
    assertEqualsDouble(b.get.relationships(r2uuid), -0.2, 0.001)
  }

  test("Valid name and UUID and perceptions") {
    val u = UUID.randomUUID()
    val uStr = u.toString()

    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "uuid": "$uStr",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertEquals(b.get.uuid, u)
    assertEqualsDouble(b.get.perceptions(b1uuid), 0.2, 0.001)
    assertEqualsDouble(b.get.perceptions(b2uuid), -0.4, 0.001)
  }

  test("Valid name and UUID and perceptions and relationships") {
    val u = UUID.randomUUID()
    val uStr = u.toString()

    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "uuid": "$uStr",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      },
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertEquals(b.get.uuid, u)
    assertEqualsDouble(b.get.perceptions(b1uuid), 0.2, 0.001)
    assertEqualsDouble(b.get.perceptions(b2uuid), -0.4, 0.001)
    assertEqualsDouble(b.get.relationships(r1uuid), 0.3, 0.001)
    assertEqualsDouble(b.get.relationships(r2uuid), -0.2, 0.001)
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

  test("Valid name and unspecified UUID no perceptions and relationships") {
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertNotEquals(
      b.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
    assertEqualsDouble(b.get.relationships(r1uuid), 0.3, 0.001)
    assertEqualsDouble(b.get.relationships(r2uuid), -0.2, 0.001)
  }

  test("Valid name and unspecified UUID perceptions") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertNotEquals(
      b.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
    assertEqualsDouble(b.get.perceptions(b1uuid), 0.2, 0.001)
    assertEqualsDouble(b.get.perceptions(b2uuid), -0.4, 0.001)
  }

  test("Valid name and unspecified UUID perceptions and relationships") {
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      },
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.name, "Belief 1")
    assertNotEquals(
      b.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
    assertEqualsDouble(b.get.perceptions(b1uuid), 0.2, 0.001)
    assertEqualsDouble(b.get.perceptions(b2uuid), -0.4, 0.001)
    assertEqualsDouble(b.get.relationships(r1uuid), 0.3, 0.001)
    assertEqualsDouble(b.get.relationships(r2uuid), -0.2, 0.001)
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

  test("Invalid name and unspecified UUID no perceptions and relationships") {
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid name and unspecified UUID perceptions") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid name and unspecified UUID perceptions and relationships") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      },
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
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

  test("Invalid name and valid UUID no perceptions and relationships") {
    val u = UUID.randomUUID()
    val uStr = u.toString()

    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "uuid": "$uStr",
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid name and valid UUID perceptions") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "uuid": "$uStr",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Invalid name and valid UUID perceptions and relationships") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "uuid": "$uStr",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      },
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
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

  test("Valid name and invalid UUID no perceptions and relationships") {
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "uuid": "aaa",
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
    }
    """)

    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Valid name and invalid UUID perceptions") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": 2,
      "uuid": "aaa",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      }
    }
    """)
    val b = json.validate[BeliefSpec]
    assert(!b.isSuccess, "JSON read successful")
  }

  test("Valid name and invalid UUID perceptions and relationships") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()
    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "name": "Belief 1",
      "uuid": "aaa",
      "perceptions": {
        "$b1uuid": 0.2,
        "$b2uuid": -0.4
      },
      "relationships": {
        "$r1uuid": 0.3,
        "$r2uuid": -0.2
      }
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

    assertEquals(b.get(0).perceptions.size, 0)
    assertEquals(b.get(0).relationships.size, 0)
    assertEquals(b.get(1).perceptions.size, 0)
    assertEquals(b.get(1).relationships.size, 0)
  }

  test("Array of valid BeliefSpecs no perceptions and relationships") {
    val u = UUID.randomUUID()
    val uStr = u.toString()

    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()
    val r3uuid = UUID.randomUUID()
    val r4uuid = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
      [
        {
          "name": "B1",
          "relationships": {
            "$r1uuid": -0.1,
            "$r2uuid": 0.9,
            "$r4uuid": 0.22
          }
        },
        {
          "name": "B2",
          "uuid": "$uStr",
          "relationships": {
            "$r1uuid": -0.4,
            "$r2uuid": 0.11,
            "$r3uuid": -0.94,
            "$r4uuid": 0.4
          }
        }
      ]
    """)
    val b = json.validate[Array[BeliefSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).name, "B1")
    assertEquals(b.get(1).name, "B2")
    assertEquals(b.get(1).uuid, u)

    assertEquals(b.get(0).perceptions.size, 0)
    assertEquals(b.get(0).relationships.size, 3)
    assertEquals(b.get(1).perceptions.size, 0)
    assertEquals(b.get(1).relationships.size, 4)

    assertEqualsDouble(b.get(0).relationships(r1uuid), -0.1, 0.001)
    assertEqualsDouble(b.get(0).relationships(r2uuid), 0.9, 0.001)
    assertEqualsDouble(b.get(0).relationships(r4uuid), 0.22, 0.001)
    assertEqualsDouble(b.get(1).relationships(r1uuid), -0.4, 0.001)
    assertEqualsDouble(b.get(1).relationships(r2uuid), 0.11, 0.001)
    assertEqualsDouble(b.get(1).relationships(r3uuid), -0.94, 0.001)
    assertEqualsDouble(b.get(1).relationships(r4uuid), 0.4, 0.001)
  }

  test("Array of valid BeliefSpecs perceptions") {
    val u = UUID.randomUUID()
    val uStr = u.toString()
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()
    val b3uuid = UUID.randomUUID()
    val b4uuid = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
      [
        {
          "name": "B1",
          "perceptions": {
            "$b1uuid": 0.2,
            "$b2uuid": -0.4
          }
        },
        {
          "name": "B2",
          "uuid": "$uStr",
          "perceptions": {
            "$b1uuid": 0.1,
            "$b2uuid": -0.2,
            "$b3uuid": 0.9,
            "$b4uuid": -0.33
          }
        }
      ]
    """)
    val b = json.validate[Array[BeliefSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).name, "B1")
    assertEquals(b.get(1).name, "B2")
    assertEquals(b.get(1).uuid, u)

    assertEquals(b.get(0).perceptions.size, 2)
    assertEquals(b.get(1).perceptions.size, 4)
    assertEquals(b.get(0).relationships.size, 0)
    assertEquals(b.get(1).relationships.size, 0)

    assertEqualsDouble(b.get(0).perceptions(b1uuid), 0.2, 0.001)
    assertEqualsDouble(b.get(0).perceptions(b2uuid), -0.4, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b1uuid), 0.1, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b2uuid), -0.2, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b3uuid), 0.9, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b4uuid), -0.33, 0.001)
  }

  test("Array of valid BeliefSpecs perceptions and relationships") {
    val u = UUID.randomUUID()
    val uStr = u.toString()

    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()
    val b3uuid = UUID.randomUUID()
    val b4uuid = UUID.randomUUID()

    val r1uuid = UUID.randomUUID()
    val r2uuid = UUID.randomUUID()
    val r3uuid = UUID.randomUUID()
    val r4uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
      [
        {
          "name": "B1",
          "perceptions": {
            "$b1uuid": 0.2,
            "$b2uuid": -0.4
          },
          "relationships": {
            "$r1uuid": -0.1,
            "$r2uuid": 0.9,
            "$r4uuid": 0.22
          }
        },
        {
          "name": "B2",
          "uuid": "$uStr",
          "perceptions": {
            "$b1uuid": 0.1,
            "$b2uuid": -0.2,
            "$b3uuid": 0.9,
            "$b4uuid": -0.33
          },
          "relationships": {
            "$r1uuid": -0.4,
            "$r2uuid": 0.11,
            "$r3uuid": -0.94,
            "$r4uuid": 0.4
          }
        }
      ]
    """)
    val b = json.validate[Array[BeliefSpec]]
    assert(b.isSuccess, "JSON read unsuccessful")
    assertEquals(b.get.length, 2)
    assertEquals(b.get(0).name, "B1")
    assertEquals(b.get(1).name, "B2")
    assertEquals(b.get(1).uuid, u)

    assertEquals(b.get(0).perceptions.size, 2)
    assertEquals(b.get(1).perceptions.size, 4)
    assertEquals(b.get(0).relationships.size, 3)
    assertEquals(b.get(1).relationships.size, 4)

    assertEqualsDouble(b.get(0).perceptions(b1uuid), 0.2, 0.001)
    assertEqualsDouble(b.get(0).perceptions(b2uuid), -0.4, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b1uuid), 0.1, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b2uuid), -0.2, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b3uuid), 0.9, 0.001)
    assertEqualsDouble(b.get(1).perceptions(b4uuid), -0.33, 0.001)

    assertEqualsDouble(b.get(0).relationships(r1uuid), -0.1, 0.001)
    assertEqualsDouble(b.get(0).relationships(r2uuid), 0.9, 0.001)
    assertEqualsDouble(b.get(0).relationships(r4uuid), 0.22, 0.001)
    assertEqualsDouble(b.get(1).relationships(r1uuid), -0.4, 0.001)
    assertEqualsDouble(b.get(1).relationships(r2uuid), 0.11, 0.001)
    assertEqualsDouble(b.get(1).relationships(r3uuid), -0.94, 0.001)
    assertEqualsDouble(b.get(1).relationships(r4uuid), 0.4, 0.001)
  }

  test("toBasicBelief works no perceptions") {
    val u = UUID.randomUUID()
    val bi: BeliefSpec = BeliefSpec("b1", u)
    val bo: BasicBelief = bi.toBasicBelief()

    assertEquals(bo.name, "b1")
    assertEquals(bo.uuid, u)
  }

  test("toBasicBelief works no perceptions and relationships") {
    val u = UUID.randomUUID()

    val relationships = Map(
      UUID.randomUUID() -> 0.2,
      UUID.randomUUID() -> 0.5
    )

    val bi: BeliefSpec = BeliefSpec("b1", u, relationships = relationships)
    val bo: BasicBelief = bi.toBasicBelief()

    assertEquals(bo.name, "b1")
    assertEquals(bo.uuid, u)
  }

  test("toBasicBelief works perceptions") {
    val u = UUID.randomUUID()

    val behaviours =
      Array(
        BasicBehaviour("b1"),
        BasicBehaviour("b2")
      )

    val perceptions =
      Map(
        behaviours(0).uuid -> 0.2,
        behaviours(1).uuid -> -0.5
      )

    val bi: BeliefSpec = BeliefSpec("b1", u, perceptions)
    val bo: BasicBelief = bi.toBasicBelief(behaviours)

    assertEquals(bo.name, "b1")
    assertEquals(bo.uuid, u)

    assertEqualsDouble(bo.getPerception(behaviours(0)).get, 0.2, 0.001)
    assertEqualsDouble(bo.getPerception(behaviours(1)).get, -0.5, 0.001)
  }

  test("toBasicBelief works perceptions and relationships") {
    val u = UUID.randomUUID()

    val behaviours =
      Array(
        BasicBehaviour("b1"),
        BasicBehaviour("b2")
      )

    val perceptions =
      Map(
        behaviours(0).uuid -> 0.2,
        behaviours(1).uuid -> -0.5
      )

    val relationships = Map(
      UUID.randomUUID() -> 0.2,
      UUID.randomUUID() -> 0.5
    )

    val bi: BeliefSpec = BeliefSpec("b1", u, perceptions, relationships)
    val bo: BasicBelief = bi.toBasicBelief(behaviours)

    assertEquals(bo.name, "b1")
    assertEquals(bo.uuid, u)

    assertEqualsDouble(bo.getPerception(behaviours(0)).get, 0.2, 0.001)
    assertEqualsDouble(bo.getPerception(behaviours(1)).get, -0.5, 0.001)
  }

  test("linkBeliefRelationships with beliefs and relationships") {
    val beliefs = Array(
      BasicBelief("b1"),
      BasicBelief("b2")
    )

    val relationships = Map(
      beliefs(0).uuid -> 0.2,
      beliefs(1).uuid -> 0.5
    )

    val bi: BeliefSpec =
      BeliefSpec("b1", beliefs(0).uuid, relationships = relationships)

    bi.linkBeliefRelationships(beliefs)

    assertEqualsDouble(beliefs(0).getRelationship(beliefs(0)).get, 0.2, 0.001)
    assertEqualsDouble(beliefs(0).getRelationship(beliefs(1)).get, 0.5, 0.001)
  }
}
