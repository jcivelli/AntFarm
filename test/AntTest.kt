import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import java.awt.Dimension
import java.awt.Point

internal class AntTest {

    private val terrainModel = object : TerrainModel {
        override val dimensions = Dimension(30, 40)
        override val nestPosition = Point(0, 0)
        override val ants = emptyList<Ant>()
    }

    private val neCorner = Point(terrainModel.dimensions.width - 1, terrainModel.dimensions.height - 1)
    private val seCorner = Point(terrainModel.dimensions.width - 1, 0)
    private val swCorner = Point(0, 0)
    private val nwCorner = Point(0, terrainModel.dimensions.height - 1)

    // Points touching walls (and not corners).
    private val nWall = Point(terrainModel.dimensions.width / 2, terrainModel.dimensions.height - 1)
    private val eWall = Point(terrainModel.dimensions.width - 1, terrainModel.dimensions.height / 2)
    private val sWall = Point(terrainModel.dimensions.width / 2, 0)
    private val wWall = Point(0, terrainModel.dimensions.height / 2)

    private val ant = Ant(0, terrainModel)

    private val positionChangeListener: Ant.PositionListener = mock()

    @Test
    fun move_corner_sw() {
        // Heading SW, coin flip is HEAD.
        ant.position = swCorner
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,0, 1))
        reset(positionChangeListener)

        // Heading SW, coin flip is TAIL.
        ant.position = swCorner
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.EAST)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,1, 0))
        reset(positionChangeListener)

        // Heading S.
        ant.position = swCorner
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,1, 0))
        reset(positionChangeListener)

        // Heading W.
        ant.position = swCorner
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,0, 1))
    }

    @Test
    fun move_corner_nw() {
        // Heading NW, coin flip is HEAD.
        ant.position = nwCorner
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 0, -1))
        reset(positionChangeListener)

        // Heading NW, coin flip is TAIL.
        ant.position = nwCorner
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.EAST)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 1, 0))
        reset(positionChangeListener)

        // Heading N.
        ant.position = nwCorner
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.EAST)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 1, 0))
        reset(positionChangeListener)

        // Heading W.
        ant.position = nwCorner
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 0, -1))
    }

    @Test
    fun move_corner_ne() {
        // Heading NE, coin flip is HEAD.
        ant.position = neCorner
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, 0, -1))
        reset(positionChangeListener)

        // Heading NE, coin flip is TAIL.
        ant.position = neCorner
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.WEST)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, -1, 0))
        reset(positionChangeListener)

        // Heading N.
        ant.position = neCorner
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.WEST)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, -1, 0))
        reset(positionChangeListener)

        // Heading E.
        ant.position = neCorner
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, 0, -1))
    }

    @Test
    fun move_corner_se() {
        // Heading SE, coin flip is HEAD.
        ant.position = seCorner
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, 0, 1))
        reset(positionChangeListener)

        // Heading SE, coin flip is TAIL.
        ant.position = seCorner
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.WEST)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, -1, 0))
        reset(positionChangeListener)

        // Heading S.
        ant.position = seCorner
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.WEST)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, -1, 0))
        reset(positionChangeListener)

        // Heading E.
        ant.position = seCorner
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.NORTH)
    }

    @Test
    fun move_wall_n() {
        // Heading N, coin flip is HEAD.
        ant.position = nWall
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.EAST)
        reset(positionChangeListener)

        // Heading N, coin flip is TAIL.
        ant.position = nWall
        ant.direction = Direction.NORTH
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.WEST)
        reset(positionChangeListener)

        // Heading NW, coin flip is HEAD, follow the wall.
        ant.position = nWall
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.WEST)
        reset(positionChangeListener)

        // Heading NW, coin flip is TAIL, bounce off the wall.
        ant.position = nWall
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, -1, -1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.SOUTH_WEST)
        reset(positionChangeListener)

        // Heading NE, coin flip is HEAD, follow the wall.
        ant.position = nWall
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.EAST)
        reset(positionChangeListener)

        // Heading NE, coin flip is TAIL, bounce off the wall.
        ant.position = nWall
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, 1, -1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.SOUTH_EAST)
        reset(positionChangeListener)
    }

    @Test
    fun move_wall_e() {
        // Heading E, coin flip is HEAD.
        ant.position = eWall
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading E, coin flip is TAIL.
        ant.position = eWall
        ant.direction = Direction.EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0,-1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SE, coin flip is HEAD, follow the wall.
        ant.position = eWall
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0,-1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SE, coin flip is TAIL, bounce off the wall.
        ant.position = eWall
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, -1,-1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.SOUTH_WEST)
        reset(positionChangeListener)

        // Heading NE, coin flip is HEAD, follow the wall.
        ant.position = eWall
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0,1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading NE, coin flip is HEAD, bounce off the wall.
        ant.position = eWall
        ant.direction = Direction.NORTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, -1,1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.NORTH_WEST)
    }

    @Test
    fun move_wall_s() {
        // Heading S, coin flip is HEAD.
        ant.position = sWall
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
        reset(positionChangeListener)

        // Heading S, coin flip is TAIL.
        ant.position = sWall
        ant.direction = Direction.SOUTH
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.WEST)
        reset(positionChangeListener)

        // Heading SE, coin flip is HEAD, follow the wall.
        ant.position = sWall
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.EAST)
        reset(positionChangeListener)

        // Heading SE, coin flip is TAIL, bounce off the wall.
        ant.position = sWall
        ant.direction = Direction.SOUTH_EAST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, 1, 1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.NORTH_EAST)
        reset(positionChangeListener)

        // Heading SW, coin flip is HEAD, follow the wall.
        ant.position = sWall
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.WEST)
        reset(positionChangeListener)

        // Heading SW, coin flip is HEAD, bounce off the wall.
        ant.position = sWall
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, -1, 1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.NORTH_WEST)
    }

    @Test
    fun move_wall_w() {
        // Heading W, coin flip is HEAD.
        ant.position = wWall
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading W, coin flip is TAIL.
        ant.position = wWall
        ant.direction = Direction.WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, -1))
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SW, coin flip is HEAD, follow the wall.
        ant.position = wWall
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, -1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SW, coin flip is TAIL, bounce off the wall.
        ant.position = wWall
        ant.direction = Direction.SOUTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 1, -1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.SOUTH_EAST)
        reset(positionChangeListener)

        // Heading NW, coin flip is HEAD, follow the wall.
        ant.position = wWall
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.HEAD), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading NW, coin flip is HEAD, bounce off the wall.
        ant.position = wWall
        ant.direction = Direction.NORTH_WEST
        ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 1, 1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.NORTH_EAST)
    }

    @Test
    fun move_noWall_traverseHorizontally() {
        ant.position = Point(0, 0)
        for (y in 0 until terrainModel.dimensions.height) {
            ant.direction = if (ant.position.x == 0) Direction.EAST else Direction.WEST
            for (x in 0 until terrainModel.dimensions.width - 1) {
                val beforePosition = Point(ant.position)
                val afterPosition = Point(if (ant.direction == Direction.EAST) ant.position.x + 1 else ant.position.x - 1,
                    ant.position.y)
                ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
                verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            }
            ant.position = Point(ant.position.x, ant.position.y + 1)
        }
    }

    @Test
    fun move_noWall_traverseVertically() {
        ant.position = Point(0, 0)
        for (x in 0 until terrainModel.dimensions.width) {
            ant.direction = if (ant.position.y == 0) Direction.NORTH else Direction.SOUTH
            for (y in 0 until terrainModel.dimensions.height - 1) {
                val beforePosition = Point(ant.position)
                val afterPosition = Point(ant.position.x, if (ant.direction == Direction.SOUTH) ant.position.y - 1 else ant.position.y + 1)
                ant.move(1, TestFlipper(CoinFlip.TAIL), positionChangeListener)
                verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            }
            ant.position = Point(ant.position.x + 1, ant.position.y)
        }
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