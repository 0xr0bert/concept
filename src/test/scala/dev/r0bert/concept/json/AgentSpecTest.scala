package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.JsValue
import play.api.libs.json.Json

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
}
