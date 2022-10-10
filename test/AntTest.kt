import org.junit.Test
import org.mockito.kotlin.*
import java.awt.Dimension
import java.awt.Point
import kotlin.math.min
import kotlin.random.Random

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


    private val straightLineAnt = Ant(0, terrainModel, StraightLineAntBehavior())
    private val indecisiveAnt = Ant(0, terrainModel, object : AntBehavior {
        override fun getTravelSegmentLength() : Int {
            return 0
        }
    })

    private val positionChangeListener: Ant.PositionListener = mock()

    private fun createRandom(value: Boolean) : Random {
        val random = mock<Random>()
        whenever(random.nextBoolean()).thenReturn(value)
        return random
    }

    private fun createRandom(vararg values : Int) : Random {
        val random = mock<Random>()
        for (value in values) {
            whenever(random.nextInt(any())).thenReturn(value)
        }
        return random
    }

    @Test
    fun move_corner_sw() {
        // Heading SW, random returns true.
        straightLineAnt.position = swCorner
        straightLineAnt.direction = Direction.SOUTH_WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,0, 1))
        reset(positionChangeListener)

        // Heading SW, random returns false.
        straightLineAnt.position = swCorner
        straightLineAnt.direction = Direction.SOUTH_WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.EAST)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,1, 0))
        reset(positionChangeListener)

        // Heading S.
        straightLineAnt.position = swCorner
        straightLineAnt.direction = Direction.SOUTH
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,1, 0))
        reset(positionChangeListener)

        // Heading W.
        straightLineAnt.position = swCorner
        straightLineAnt.direction = Direction.WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(swCorner, LocationUtils.movePoint(swCorner,0, 1))
    }

    @Test
    fun move_corner_nw() {
        // Heading NW, random returns true.
        straightLineAnt.position = nwCorner
        straightLineAnt.direction = Direction.NORTH_WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 0, -1))
        reset(positionChangeListener)

        // Heading NW, random returns false.
        straightLineAnt.position = nwCorner
        straightLineAnt.direction = Direction.NORTH_WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.EAST)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 1, 0))
        reset(positionChangeListener)

        // Heading N.
        straightLineAnt.position = nwCorner
        straightLineAnt.direction = Direction.NORTH
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.EAST)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 1, 0))
        reset(positionChangeListener)

        // Heading W.
        straightLineAnt.position = nwCorner
        straightLineAnt.direction = Direction.WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(nwCorner, LocationUtils.movePoint(nwCorner, 0, -1))
    }

    @Test
    fun move_corner_ne() {
        // Heading NE, random returns true.
        straightLineAnt.position = neCorner
        straightLineAnt.direction = Direction.NORTH_EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, 0, -1))
        reset(positionChangeListener)

        // Heading NE, random returns false.
        straightLineAnt.position = neCorner
        straightLineAnt.direction = Direction.NORTH_EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.WEST)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, -1, 0))
        reset(positionChangeListener)

        // Heading N.
        straightLineAnt.position = neCorner
        straightLineAnt.direction = Direction.NORTH
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.WEST)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, -1, 0))
        reset(positionChangeListener)

        // Heading E.
        straightLineAnt.position = neCorner
        straightLineAnt.direction = Direction.EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.SOUTH)
        verify(positionChangeListener).positionChanged(neCorner, LocationUtils.movePoint(neCorner, 0, -1))
    }

    @Test
    fun move_corner_se() {
        // Heading SE, random returns true.
        straightLineAnt.position = seCorner
        straightLineAnt.direction = Direction.SOUTH_EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.NORTH)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, 0, 1))
        reset(positionChangeListener)

        // Heading SE, random returns false.
        straightLineAnt.position = seCorner
        straightLineAnt.direction = Direction.SOUTH_EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.WEST)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, -1, 0))
        reset(positionChangeListener)

        // Heading S.
        straightLineAnt.position = seCorner
        straightLineAnt.direction = Direction.SOUTH
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.WEST)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, -1, 0))
        reset(positionChangeListener)

        // Heading E.
        straightLineAnt.position = seCorner
        straightLineAnt.direction = Direction.EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(seCorner, LocationUtils.movePoint(seCorner, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.NORTH)
    }

    @Test
    fun move_wall_n() {
        // Heading N, random returns true.
        straightLineAnt.position = nWall
        straightLineAnt.direction = Direction.NORTH
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.EAST)
        reset(positionChangeListener)

        // Heading N, random returns false.
        straightLineAnt.position = nWall
        straightLineAnt.direction = Direction.NORTH
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH, Direction.WEST)
        reset(positionChangeListener)

        // Heading NW, random returns true, follow the wall.
        straightLineAnt.position = nWall
        straightLineAnt.direction = Direction.NORTH_WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.WEST)
        reset(positionChangeListener)

        // Heading NW, random returns false, bounce off the wall.
        straightLineAnt.position = nWall
        straightLineAnt.direction = Direction.NORTH_WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, -1, -1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.SOUTH_WEST)
        reset(positionChangeListener)

        // Heading NE, random returns true, follow the wall.
        straightLineAnt.position = nWall
        straightLineAnt.direction = Direction.NORTH_EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.EAST)
        reset(positionChangeListener)

        // Heading NE, random returns false, bounce off the wall.
        straightLineAnt.position = nWall
        straightLineAnt.direction = Direction.NORTH_EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(nWall, LocationUtils.movePoint(nWall, 1, -1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.SOUTH_EAST)
        reset(positionChangeListener)
    }

    @Test
    fun move_wall_e() {
        // Heading E, random returns true.
        straightLineAnt.position = eWall
        straightLineAnt.direction = Direction.EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading E, random returns false.
        straightLineAnt.position = eWall
        straightLineAnt.direction = Direction.EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0,-1))
        verify(positionChangeListener).directionChanged(Direction.EAST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SE, random returns true, follow the wall.
        straightLineAnt.position = eWall
        straightLineAnt.direction = Direction.SOUTH_EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0,-1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SE, random returns false, bounce off the wall.
        straightLineAnt.position = eWall
        straightLineAnt.direction = Direction.SOUTH_EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, -1,-1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.SOUTH_WEST)
        reset(positionChangeListener)

        // Heading NE, random returns true, follow the wall.
        straightLineAnt.position = eWall
        straightLineAnt.direction = Direction.NORTH_EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, 0,1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading NE, random returns true, bounce off the wall.
        straightLineAnt.position = eWall
        straightLineAnt.direction = Direction.NORTH_EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(eWall, LocationUtils.movePoint(eWall, -1,1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_EAST, Direction.NORTH_WEST)
    }

    @Test
    fun move_wall_s() {
        // Heading S, random returns true.
        straightLineAnt.position = sWall
        straightLineAnt.direction = Direction.SOUTH
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
        reset(positionChangeListener)

        // Heading S, random returns false.
        straightLineAnt.position = sWall
        straightLineAnt.direction = Direction.SOUTH
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.WEST)
        reset(positionChangeListener)

        // Heading SE, random returns true, follow the wall.
        straightLineAnt.position = sWall
        straightLineAnt.direction = Direction.SOUTH_EAST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, 1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.EAST)
        reset(positionChangeListener)

        // Heading SE, random returns false, bounce off the wall.
        straightLineAnt.position = sWall
        straightLineAnt.direction = Direction.SOUTH_EAST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, 1, 1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_EAST, Direction.NORTH_EAST)
        reset(positionChangeListener)

        // Heading SW, random returns true, follow the wall.
        straightLineAnt.position = sWall
        straightLineAnt.direction = Direction.SOUTH_WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, -1, 0))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.WEST)
        reset(positionChangeListener)

        // Heading SW, random returns true, bounce off the wall.
        straightLineAnt.position = sWall
        straightLineAnt.direction = Direction.SOUTH_WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(sWall, LocationUtils.movePoint(sWall, -1, 1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.NORTH_WEST)
    }

    @Test
    fun move_wall_w() {
        // Heading W, random returns true.
        straightLineAnt.position = wWall
        straightLineAnt.direction = Direction.WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading W, random returns false.
        straightLineAnt.position = wWall
        straightLineAnt.direction = Direction.WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, -1))
        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SW, random returns true, follow the wall.
        straightLineAnt.position = wWall
        straightLineAnt.direction = Direction.SOUTH_WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, -1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.SOUTH)
        reset(positionChangeListener)

        // Heading SW, random returns false, bounce off the wall.
        straightLineAnt.position = wWall
        straightLineAnt.direction = Direction.SOUTH_WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 1, -1))
        verify(positionChangeListener).directionChanged(Direction.SOUTH_WEST, Direction.SOUTH_EAST)
        reset(positionChangeListener)

        // Heading NW, random returns true, follow the wall.
        straightLineAnt.position = wWall
        straightLineAnt.direction = Direction.NORTH_WEST
        straightLineAnt.move(createRandom(true), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 0, 1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.NORTH)
        reset(positionChangeListener)

        // Heading NW, random returns true, bounce off the wall.
        straightLineAnt.position = wWall
        straightLineAnt.direction = Direction.NORTH_WEST
        straightLineAnt.move(createRandom(false), positionChangeListener)
        verify(positionChangeListener).positionChanged(wWall, LocationUtils.movePoint(wWall, 1, 1))
        verify(positionChangeListener).directionChanged(Direction.NORTH_WEST, Direction.NORTH_EAST)
    }

    @Test
    fun move_noWall_traverseHorizontally() {
        straightLineAnt.position = Point(0, 0)
        for (y in 0 until terrainModel.dimensions.height) {
            straightLineAnt.direction = if (straightLineAnt.position.x == 0) Direction.EAST else Direction.WEST
            for (x in 0 until terrainModel.dimensions.width - 1) {
                val beforePosition = Point(straightLineAnt.position)
                val afterPosition = Point(if (straightLineAnt.direction == Direction.EAST) straightLineAnt.position.x + 1 else straightLineAnt.position.x - 1,
                    straightLineAnt.position.y)
                straightLineAnt.move(createRandom(false), positionChangeListener)
                verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            }
            straightLineAnt.position = Point(straightLineAnt.position.x, straightLineAnt.position.y + 1)
        }
    }

    @Test
    fun move_noWall_traverseVertically() {
        straightLineAnt.position = Point(0, 0)
        for (x in 0 until terrainModel.dimensions.width) {
            straightLineAnt.direction = if (straightLineAnt.position.y == 0) Direction.NORTH else Direction.SOUTH
            for (y in 0 until terrainModel.dimensions.height - 1) {
                val beforePosition = Point(straightLineAnt.position)
                val afterPosition = Point(straightLineAnt.position.x, if (straightLineAnt.direction == Direction.SOUTH) straightLineAnt.position.y - 1 else straightLineAnt.position.y + 1)
                straightLineAnt.move(createRandom(false), positionChangeListener)
                verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
                verify(positionChangeListener, never()).directionChanged(any(), any())
            }
            straightLineAnt.position = Point(straightLineAnt.position.x + 1, straightLineAnt.position.y)
        }
    }

    @Test
    fun move_noWall_traverseDiagonally_swToNe() {
        val length = min(terrainModel.dimensions.width, terrainModel.dimensions.height)

        straightLineAnt.position = Point(0, 0)
        straightLineAnt.direction = Direction.NORTH_EAST
        for (i in 0 until length - 1) {
            val beforePosition = Point(straightLineAnt.position)
            val afterPosition = Point(straightLineAnt.position.x + 1, straightLineAnt.position.y + 1)
            straightLineAnt.move(createRandom(true), positionChangeListener)
            verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            verify(positionChangeListener, never()).directionChanged(any(), any())
        }
    }

    @Test
    fun move_noWall_traverseDiagonally_neToSw() {
        val length = min(terrainModel.dimensions.width, terrainModel.dimensions.height)

        straightLineAnt.position = Point(length - 1, length - 1)
        straightLineAnt.direction = Direction.SOUTH_WEST
        for (i in 0 until length - 1) {
            val beforePosition = Point(straightLineAnt.position)
            val afterPosition = Point(straightLineAnt.position.x - 1, straightLineAnt.position.y - 1)
            straightLineAnt.move(createRandom(true), positionChangeListener)
            verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            verify(positionChangeListener, never()).directionChanged(any(), any())
        }
    }

    @Test
    fun move_noWall_traverseDiagonally_nwToSe() {
        val length = min(terrainModel.dimensions.width, terrainModel.dimensions.height)

        straightLineAnt.position = Point(0, length - 1)
        straightLineAnt.direction = Direction.SOUTH_EAST
        for (i in 0 until length - 1) {
            val beforePosition = Point(straightLineAnt.position)
            val afterPosition = Point(straightLineAnt.position.x + 1, straightLineAnt.position.y - 1)
            straightLineAnt.move(createRandom(true), positionChangeListener)
            verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            verify(positionChangeListener, never()).directionChanged(any(), any())
        }
    }

    @Test
    fun move_noWall_traverseDiagonally_seToNw() {
        val length = min(terrainModel.dimensions.width, terrainModel.dimensions.height)

        straightLineAnt.position = Point(length - 1, 0)
        straightLineAnt.direction = Direction.NORTH_WEST
        for (i in 0 until length - 1) {
            val beforePosition = Point(straightLineAnt.position)
            val afterPosition = Point(straightLineAnt.position.x - 1, straightLineAnt.position.y + 1)
            straightLineAnt.move(createRandom(true), positionChangeListener)
            verify(positionChangeListener).positionChanged(beforePosition, afterPosition)
            verify(positionChangeListener, never()).directionChanged(any(), any())
        }
    }

    @Test
    fun move_reevalDirection_wWall() {
        indecisiveAnt.position = Point(0, terrainModel.dimensions.height / 2)
        indecisiveAnt.direction = Direction.SOUTH

        // Continue south.
        indecisiveAnt.move(createRandom(1), positionChangeListener)
        verify(positionChangeListener).positionChanged(any(), any())
        verify(positionChangeListener, never()).directionChanged(any(), any())

        //
//        indecisiveAnt.move(createRandom(1), positionChangeListener)
//        verify(positionChangeListener).positionChanged(any(), any())
//        verify(positionChangeListener, never()).directionChanged(any(), any())



    }

//    @Test
//    fun move_noWall_multipleSteps() {
//        val expectedPositions  =  arrayOf(
//            Point(0, 0),
//            Point(1, 1),
//            Point(2, 2),
//            Point(3, 3),
//            Point(4, 4),
//            Point(5, 5),
//            )
//
//        ant.position = expectedPositions[0]
//        ant.direction = Direction.NORTH_EAST
//
//        ant.move(5, createRandom(true), positionChangeListener)
//        verify(positionChangeListener).positionChanged(expectedPositions[0], expectedPositions[1])
//        verify(positionChangeListener).positionChanged(expectedPositions[1], expectedPositions[2])
//        verify(positionChangeListener).positionChanged(expectedPositions[2], expectedPositions[3])
//        verify(positionChangeListener).positionChanged(expectedPositions[3], expectedPositions[4])
//        verify(positionChangeListener).positionChanged(expectedPositions[4], expectedPositions[5])
//        verify(positionChangeListener, never()).directionChanged(any(), any())
//    }
//
//    @Test
//    fun move_wall_multipleSteps() {
//        val expectedPositions  =  arrayOf(
//            Point(1, 1),
//            Point(0, 1),
//            Point(0, 0),
//            Point(1, 0),
//            Point(2, 0),
//            Point(3, 0),
//        )
//
//        ant.position = expectedPositions[0]
//        ant.direction = Direction.WEST
//
//        ant.move(5, createRandom(false), positionChangeListener)
//        verify(positionChangeListener).positionChanged(expectedPositions[0], expectedPositions[1])
//        verify(positionChangeListener).positionChanged(expectedPositions[1], expectedPositions[2])
//        verify(positionChangeListener).positionChanged(expectedPositions[2], expectedPositions[3])
//        verify(positionChangeListener).positionChanged(expectedPositions[3], expectedPositions[4])
//        verify(positionChangeListener).positionChanged(expectedPositions[4], expectedPositions[5])
//        verify(positionChangeListener).directionChanged(Direction.WEST, Direction.SOUTH)
//        verify(positionChangeListener).directionChanged(Direction.SOUTH, Direction.EAST)
//    }
}