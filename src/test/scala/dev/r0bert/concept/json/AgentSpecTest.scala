package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import dev.r0bert.beliefspread.core.BasicBehaviour

class AgentSpecTest extends munit.FunSuite {
  test("Valid UUID") {
    val uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "uuid": "${uuid.toString()}"
    }
    """)
    val a = json.validate[AgentSpec]
    assert(a.isSuccess, "JSON should be valid")
    assertEquals(a.get.uuid, uuid)
  }

  test("Unspecified UUID") {
    val json: JsValue = Json.parse("""
    {
    }
    """)
    val a = json.validate[AgentSpec]
    assert(a.isSuccess, "JSON should be valid")
    assertNotEquals(
      a.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
  }

  test("Invalid UUID") {
    val json: JsValue = Json.parse("""
    {
      "uuid": "invalid"
    }
    """)
    val a = json.validate[AgentSpec]
    assert(!a.isSuccess, "JSON should be invalid")
  }

  test("toBasicAgent with valid UUID") {
    val u = UUID.randomUUID()
    val ai = AgentSpec(u)
    val ao = ai.toBasicAgent()

    assertEquals(ao.uuid, u)
  }

  test("toBasicAgent with unspecified UUID") {
    val ai = AgentSpec()
    val ao = ai.toBasicAgent()

    assertEquals(ao.uuid, ai.uuid)
  }

  test("Valid UUID, actions") {
    val uuid = UUID.randomUUID()
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse(s"""
    {
      "uuid": "${uuid.toString()}",
      "actions": {
        "1": "$b1uuid",
        "2": "$b2uuid"
      }
    }
    """)
    val a = json.validate[AgentSpec]
    assert(a.isSuccess, "JSON should be valid")
    assertEquals(a.get.uuid, uuid)
    assertEquals(a.get.actions.size, 2)
    assertEquals(a.get.actions(1), b1uuid)
    assertEquals(a.get.actions(2), b2uuid)
  }

  test("Unspecified UUID, actions") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()
    val json: JsValue = Json.parse(s"""
    {
      "actions": {
        "1": "$b1uuid",
        "2": "$b2uuid"
      }
    }
    """)
    val a = json.validate[AgentSpec]
    assert(a.isSuccess, "JSON should be valid")
    assertNotEquals(
      a.get.uuid,
      UUID.fromString("00000000-0000-0000-0000-000000000000")
    )
    assertEquals(a.get.actions.size, 2)
    assertEquals(a.get.actions(1), b1uuid)
    assertEquals(a.get.actions(2), b2uuid)
  }

  test("Invalid UUID, actions") {
    val b1uuid = UUID.randomUUID()
    val b2uuid = UUID.randomUUID()

    val json: JsValue = Json.parse("""
    {
      "uuid": "invalid",
      "actions": {
        "1": "$b1uuid",
        "2": "$b2uuid"
      }
    }
    """)
    val a = json.validate[AgentSpec]
    assert(!a.isSuccess, "JSON should be invalid")
  }

  test("toBasicAgent with valid UUID, actions") {
    val u = UUID.randomUUID()
    val behaviours = Array(BasicBehaviour("b1"), BasicBehaviour("b2"))
    val actions = Map(1 -> behaviours(0).uuid, 2 -> behaviours(1).uuid)

    val ai = AgentSpec(u, actions)
    val ao = ai.toBasicAgent(behaviours)

    assertEquals(ao.uuid, u)
    assertEquals(ao.getAction(1).get, behaviours(0))
    assertEquals(ao.getAction(2).get, behaviours(1))
  }

  test("toBasicAgent with unspecified UUID, actions") {
    val behaviours = Array(BasicBehaviour("b1"), BasicBehaviour("b2"))
    val actions = Map(1 -> behaviours(0).uuid, 2 -> behaviours(1).uuid)

    val ai = AgentSpec(actions = actions)
    val ao = ai.toBasicAgent(behaviours)

    assertEquals(ao.getAction(1).get, behaviours(0))
    assertEquals(ao.getAction(2).get, behaviours(1))

    assertEquals(ao.uuid, ai.uuid)
  }
}
