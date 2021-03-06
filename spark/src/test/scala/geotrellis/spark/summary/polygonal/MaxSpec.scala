package geotrellis.spark.summary.polygonal

import geotrellis.spark._
import geotrellis.spark.io.hadoop._
import geotrellis.spark.testfiles._
import geotrellis.raster.summary.polygonal._

import geotrellis.vector._

import org.scalatest.FunSpec

class MaxSpec extends FunSpec with TestEnvironment with TestFiles {

  describe("Max Zonal Summary Operation") {
    val inc = IncreasingTestFile

    val tileLayout = inc.metadata.tileLayout
    val count = (inc.count * tileLayout.tileCols * tileLayout.tileRows).toInt
    val totalExtent = inc.metadata.extent

    it("should get correct max over whole raster extent") {
      inc.polygonalMax(totalExtent.toPolygon) should be(count - 1)
    }

    it("should get correct max over a quarter of the extent") {
      val xd = totalExtent.xmax - totalExtent.xmin
      val yd = totalExtent.ymax - totalExtent.ymin

      val quarterExtent = Extent(
        totalExtent.xmin,
        totalExtent.ymin,
        totalExtent.xmin + xd / 2,
        totalExtent.ymin + yd / 2
      )

      val result = inc.polygonalMax(quarterExtent.toPolygon)
      val expected = inc.stitch.tile.polygonalMax(totalExtent, quarterExtent.toPolygon)

      result should be (expected)
    }
  }

  describe("Max Zonal Summary Operation (collections api)") {
    val inc = IncreasingTestFile.toCollection

    val tileLayout = inc.metadata.tileLayout
    val count = inc.length * tileLayout.tileCols * tileLayout.tileRows
    val totalExtent = inc.metadata.extent

    it("should get correct max over whole raster extent") {
      inc.polygonalMax(totalExtent.toPolygon) should be(count - 1)
    }

    it("should get correct max over a quarter of the extent") {
      val xd = totalExtent.xmax - totalExtent.xmin
      val yd = totalExtent.ymax - totalExtent.ymin

      val quarterExtent = Extent(
        totalExtent.xmin,
        totalExtent.ymin,
        totalExtent.xmin + xd / 2,
        totalExtent.ymin + yd / 2
      )

      val result = inc.polygonalMax(quarterExtent.toPolygon)
      val expected = inc.stitch.tile.polygonalMax(totalExtent, quarterExtent.toPolygon)

      result should be (expected)
    }
  }
}
