package geotrellis.doc.examples.spark

import geotrellis.spark._
import geotrellis.spark.io.index._

import org.scalatest._
import spray.json._
import spray.json.DefaultJsonProtocol._

// --- //

class ShardingKeyIndexSpec extends FunSpec with Matchers {
  /* Z-Curve Indices */
  val zspace: KeyIndex[SpatialKey] =
    ZCurveKeyIndexMethod.createIndex(KeyBounds(
      SpatialKey(0,0),
      SpatialKey(9,9)
    ))

  val zspaceTime: KeyIndex[SpaceTimeKey] =
    ZCurveKeyIndexMethod.byDay.createIndex(KeyBounds(
      SpaceTimeKey(0, 0, 1),
      SpaceTimeKey(9, 9, 10)
    ))

  /* Hilbert Indices */
  val hspace: KeyIndex[SpatialKey] =
    HilbertKeyIndexMethod.createIndex(KeyBounds(
      SpatialKey(0, 0), SpatialKey(9, 9))
    )

  val hspaceTime: KeyIndex[SpaceTimeKey] =
    HilbertKeyIndexMethod(10).createIndex(KeyBounds(
      SpaceTimeKey(0, 0, 1), SpaceTimeKey(10, 10, 100))
    )

  describe("Index creation") {
    it("object construction") {
      new ShardingKeyIndex(zspace, 5)
      new ShardingKeyIndex(zspaceTime, 5)
      new ShardingKeyIndex(hspace, 5)
      new ShardingKeyIndex(hspaceTime, 5)
    }
  }

  describe("JsonFormat") {
    it("Z-Space Isomorphism") {
      val index: KeyIndex[SpatialKey] = new ShardingKeyIndex(zspace, 5)

      index.toJson.convertTo[KeyIndex[SpatialKey]]
    }

    it("Z-Time Isomorphism") {
      val index: KeyIndex[SpaceTimeKey] = new ShardingKeyIndex(zspaceTime, 5)

      index.toJson.convertTo[KeyIndex[SpaceTimeKey]]
    }

    it("H-Space Isomorphism") {
      val index: KeyIndex[SpatialKey] = new ShardingKeyIndex(hspace, 5)

      index.toJson.convertTo[KeyIndex[SpatialKey]]
    }

    it("H-Time Isomorphism") {
      val index: KeyIndex[SpaceTimeKey] = new ShardingKeyIndex(hspaceTime, 5)

      index.toJson.convertTo[KeyIndex[SpaceTimeKey]]
    }

  }
}
