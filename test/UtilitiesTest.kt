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

    @Test
    fun locationUtilitiesWallCollisionTest_NECorner() {
        val dimension = Dimension(100, 100)

        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.NORTH)).isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.NORTH_EAST)).isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.EAST)).isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.SOUTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.SOUTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.SOUTH_WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 99), Direction.NORTH_WEST)).isEqualTo(Direction.NORTH)
    }

    @Test
    fun locationUtilitiesWallCollisionTest_SECorner() {
        val dimension = Dimension(100, 100)

        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.NORTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.NORTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.EAST)).isEqualTo(Direction.SOUTH_EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.SOUTH_EAST)).isEqualTo(Direction.SOUTH_EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.SOUTH)).isEqualTo(Direction.SOUTH_EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.SOUTH_WEST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 0), Direction.NORTH_WEST)).isNull()
    }

    @Test
    fun locationUtilitiesWallCollisionTest_SWCorner() {
        val dimension = Dimension(100, 100)

        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.NORTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.NORTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.SOUTH_EAST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.SOUTH)).isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.SOUTH_WEST)).isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.WEST)).isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 0), Direction.NORTH_WEST)).isEqualTo(Direction.WEST)
    }

    @Test
    fun locationUtilitiesWallCollisionTest_NWCorner() {
        val dimension = Dimension(100, 100)

        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.NORTH)).isEqualTo(Direction.NORTH_WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.NORTH_EAST)).isEqualTo(Direction.NORTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.SOUTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.SOUTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.SOUTH_WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.WEST)).isEqualTo(Direction.NORTH_WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 99), Direction.NORTH_WEST)).isEqualTo(Direction.NORTH_WEST)
    }

    @Test
    fun locationUtilitiesWallCollisionTest_NWall() {
        val dimension = Dimension(100, 100)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.NORTH))
            .isEqualTo(Direction.NORTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.NORTH_EAST))
            .isEqualTo(Direction.NORTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.SOUTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.SOUTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.SOUTH_WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 99), Direction.NORTH_WEST))
            .isEqualTo(Direction.NORTH)
    }

    @Test
    fun locationUtilitiesWallCollisionTest_EWall() {
        val dimension = Dimension(100, 100)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.NORTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.NORTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.SOUTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.SOUTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.SOUTH_WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(99, 50), Direction.NORTH_WEST)).isNull()
    }

    @Test
    fun locationUtilitiesWallCollisionTest_SWall() {
        val dimension = Dimension(100, 100)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.NORTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.NORTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.SOUTH_EAST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.SOUTH)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.SOUTH_WEST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 0), Direction.NORTH_WEST)).isNull()
    }

    @Test
    fun locationUtilitiesWallCollisionTest_WWall() {
        val dimension = Dimension(100, 100)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.NORTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.NORTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.SOUTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.SOUTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.SOUTH_WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(0, 50), Direction.NORTH_WEST)).isEqualTo(Direction.WEST)
    }

    @Test
    fun locationUtilitiesWallCollisionTest_noWallNoCorner() {
        val dimension = Dimension(100, 200)

        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(1, 1), Direction.SOUTH_WEST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(98, 198), Direction.NORTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(98, 1), Direction.SOUTH_EAST)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(98, 198), Direction.SOUTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(1, 1), Direction.NORTH)).isNull()
        Truth.assertThat(LocationUtils.wallCollisionTest(dimension, Point(50, 50), Direction.EAST)).isNull()
    }

    @Test
    fun movePoint() {
        val originalPoint = Point(10, 20)
        Truth.assertThat(LocationUtils.movePoint(originalPoint, 0, 0)).isEqualTo(Point(10, 20))
        Truth.assertThat(LocationUtils.movePoint(originalPoint, 5, 0)).isEqualTo(Point(15, 20))
        Truth.assertThat(LocationUtils.movePoint(originalPoint, 0, -5)).isEqualTo(Point(10, 15))
        Truth.assertThat(LocationUtils.movePoint(originalPoint, -5, 5)).isEqualTo(Point(5, 25))
        Truth.assertThat(originalPoint).isEqualTo(Point(10, 20))
    }
}
