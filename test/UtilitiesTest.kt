import com.google.common.truth.Truth
import org.junit.Test
import java.awt.Dimension
import java.awt.Point

internal class UtilitiesTest {

    @Test
    fun locationUtilitiesWallHitTest_corners() {
        val dimension = Dimension(100, 200)

        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(0, 0))).isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(0, 199))).isEqualTo(Direction.NORTH_WEST)
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(99, 0))).isEqualTo(Direction.SOUTH_EAST)
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(99, 199))).isEqualTo(Direction.NORTH_EAST)
    }

    @Test
    fun locationUtilitiesWallHitTest_walls() {
        val dimension = Dimension(100, 200)

        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(0, 10))).isEqualTo(Direction.WEST)
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(99, 10))).isEqualTo(Direction.EAST)
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(10, 0))).isEqualTo(Direction.SOUTH)
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(10, 199))).isEqualTo(Direction.NORTH)
    }

    @Test
    fun locationUtilitiesWallHitTest_noWallsNoCorners() {
        val dimension = Dimension(100, 200)

        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(1, 1))).isNull()
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(98, 198))).isNull()
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(98, 1))).isNull()
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(98, 198))).isNull()
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(1, 1))).isNull()
        Truth.assertThat(LocationUtils.wallHitTest(dimension, Point(50, 50))).isNull()
    }
}