import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import java.awt.Dimension
import java.awt.Point

internal class AntTest {

    private val terrainModel = object : TerrainModel {
        override val dimensions = Dimension(100, 100)
        override val nestPosition = Point(0, 0)
        override val ants = emptyList<Ant>()
    }

    private val ant = Ant(0, terrainModel)

    private val positionChangeListener: Ant.PositionListener = mock()

    @Test
    fun move_corner_sw() {
        // Heading SW, coin flip is HEAD.
        ant.position = Point(0, 0)
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(Point(0, 0), Point(0, 1))
        reset(positionChangeListener)

        // Heading SW, coin flip is TAIL.
        ant.position = Point(0, 0)
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.EAST)
        verify(positionChangeListener).positionChanged(Point(0, 0), Point(1, 0))
        reset(positionChangeListener)

        // Heading S.
        ant.position = Point(0, 0)
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
        verify(positionChangeListener).positionChanged(Point(0, 0), Point(1, 0))
        reset(positionChangeListener)

        // Heading W.
        ant.position = Point(0, 0)
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(Point(0, 0), Point(0, 1))
    }

    @Test
    fun move_corner_nw() {
        // Heading NW, coin flip is HEAD.
        ant.position = Point(0, 99)
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(Point(0, 99), Point(0, 98))
        reset(positionChangeListener)

        // Heading NW, coin flip is TAIL.
        ant.position = Point(0, 99)
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.EAST)
        verify(positionChangeListener).positionChanged(Point(0, 99), Point(1, 99))
        reset(positionChangeListener)

        // Heading N.
        ant.position = Point(0, 99)
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.EAST)
        verify(positionChangeListener).positionChanged(Point(0, 99), Point(1, 99))
        reset(positionChangeListener)

        // Heading W.
        ant.position = Point(0, 99)
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(Point(0, 99), Point(0, 98))
    }

    @Test
    fun move_corner_ne() {
        // Heading NE, coin flip is HEAD.
        ant.position = Point(99, 99)
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(Point(99, 99), Point(99, 98))
        reset(positionChangeListener)

        // Heading NE, coin flip is TAIL.
        ant.position = Point(99, 99)
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.WEST)
        verify(positionChangeListener).positionChanged(Point(99, 99), Point(98, 99))
        reset(positionChangeListener)

        // Heading N.
        ant.position = Point(99, 99)
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.WEST)
        verify(positionChangeListener).positionChanged(Point(99, 99), Point(98, 99))
        reset(positionChangeListener)

        // Heading E.
        ant.position = Point(99, 99)
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(Point(99, 99), Point(99, 98))
    }

    @Test
    fun move_corner_se() {
        // Heading SE, coin flip is HEAD.
        ant.position = Point(99, 0)
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(Point(99, 0), Point(99, 1))
        reset(positionChangeListener)

        // Heading SE, coin flip is TAIL.
        ant.position = Point(99, 0)
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.WEST)
        verify(positionChangeListener).positionChanged(Point(99, 0), Point(98, 0))
        reset(positionChangeListener)

        // Heading S.
        ant.position = Point(99, 0)
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.WEST)
        verify(positionChangeListener).positionChanged(Point(99, 0), Point(98, 0))
        reset(positionChangeListener)

        // Heading E.
        ant.position = Point(99, 0)
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 0), Point(99, 1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.NORTH)
    }

    @Test
    fun move_wall_n() {
        // Heading N, coin flip is HEAD.
        ant.position = Point(10, 99)
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 99), Point(11, 99))
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.EAST)
        reset(positionChangeListener)

        // Heading N, coin flip is TAIL.
        ant.position = Point(10, 99)
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 99), Point(9, 99))
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.WEST)
        reset(positionChangeListener)

        // Heading NW, coin flip is HEAD, follow the wall.
        ant.position = Point(10, 99)
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 99), Point(9, 99))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.WEST)
        reset(positionChangeListener)

        // Heading NW, coin flip is TAIL, bounce off the wall.
        ant.position = Point(10, 99)
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 99), Point(9, 98))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.SOUTH_WEST)
        reset(positionChangeListener)

        // Heading NE, coin flip is HEAD, follow the wall.
        ant.position = Point(10, 99)
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 99), Point(11, 99))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.EAST)
        reset(positionChangeListener)

        // Heading NE, coin flip is TAIL, bounce off the wall.
        ant.position = Point(10, 99)
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 99), Point(11, 98))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.SOUTH_EAST)
        reset(positionChangeListener)
    }

    @Test
    fun move_wall_e() {
        // Heading E, coin flip is HEAD.
        ant.position = Point(99, 10)
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 10), Point(99, 11))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading E, coin flip is TAIL.
        ant.position = Point(99, 10)
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 10), Point(99, 9))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SE, coin flip is HEAD, follow the wall.
        ant.position = Point(99, 10)
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 10), Point(99, 9))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SE, coin flip is TAIL, bounce off the wall.
        ant.position = Point(99, 10)
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 10), Point(98, 9))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.SOUTH_WEST)
        reset(positionChangeListener)

        // Heading NE, coin flip is HEAD, follow the wall.
        ant.position = Point(99, 10)
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 10), Point(99, 11))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading NE, coin flip is HEAD, bounce off the wall.
        ant.position = Point(99, 10)
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(99, 10), Point(98, 11))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.NORTH_WEST)
    }

    @Test
    fun move_wall_s() {
        // Heading S, coin flip is HEAD.
        ant.position = Point(10, 0)
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 0), Point(11, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
        reset(positionChangeListener)

        // Heading S, coin flip is TAIL.
        ant.position = Point(10, 0)
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 0), Point(9, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.WEST)
        reset(positionChangeListener)

        // Heading SE, coin flip is HEAD, follow the wall.
        ant.position = Point(10, 0)
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 0), Point(11, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.EAST)
        reset(positionChangeListener)

        // Heading SE, coin flip is TAIL, bounce off the wall.
        ant.position = Point(10, 0)
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 0), Point(11, 1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.NORTH_EAST)
        reset(positionChangeListener)

        // Heading SW, coin flip is HEAD, follow the wall.
        ant.position = Point(10, 0)
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 0), Point(9, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.WEST)
        reset(positionChangeListener)

        // Heading SW, coin flip is HEAD, bounce off the wall.
        ant.position = Point(10, 0)
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(10, 0), Point(9, 1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.NORTH_WEST)
    }

    @Test
    fun move_wall_w() {
        // Heading W, coin flip is HEAD.
        ant.position = Point(0, 10)
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(0, 10), Point(0, 11))
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading W, coin flip is TAIL.
        ant.position = Point(0, 10)
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(0, 10), Point(0, 9))
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SW, coin flip is HEAD, follow the wall.
        ant.position = Point(0, 10)
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(0, 10), Point(0, 9))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SW, coin flip is TAIL, bounce off the wall.
        ant.position = Point(0, 10)
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(0, 10), Point(1, 9))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.SOUTH_EAST)
        reset(positionChangeListener)

        // Heading NW, coin flip is HEAD, follow the wall.
        ant.position = Point(0, 10)
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(0, 10), Point(0, 11))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading NW, coin flip is HEAD, bounce off the wall.
        ant.position = Point(0, 10)
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(Point(0, 10), Point(1, 11))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.NORTH_EAST)
    }

    @Test
    fun move_noWall_traverseHorizontally() {
    }

    @Test
    fun move_noWall_traverseVertically() {
    }

    @Test
    fun move_noWall_traverseDiagonally() {
    }

    @Test
    fun move_noWall_multipleSteps() {
    }

    @Test
    fun move_wall_multipleSteps() {
    }
}