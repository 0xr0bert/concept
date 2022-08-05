package dev.r0bert.concept.json

import java.util.UUID
import play.api.libs.json.Json
import play.api.libs.json.Format

final case class BehaviourSpec(
    name: String,
    uuid: UUID = UUID.randomUUID()
)
object BehaviourSpec {
  implicit val behaviourSpecFormat: Format[BehaviourSpec] =
    Json.using[Json.WithDefaultValues].format[BehaviourSpec]
}
