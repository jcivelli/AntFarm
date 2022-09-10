import java.awt.Dimension
import java.awt.Point
import java.util.Random

enum class CoinFlip {
    HEAD, TAIL;

    interface Flipper {
        fun toss() : CoinFlip
    }

    companion object RandomFlipper : Flipper {
        val random = Random()
        override fun toss(): CoinFlip  {
            return if (random.nextBoolean()) HEAD else TAIL
        }
    }
}

object LocationUtils {
    /**
     * Detects if the passed in position is next to a corner or wall and returns its position, null otherwise.
     */
    fun wallHitTest(dimension : Dimension, position: Point) : Direction? {
        // Test corners first.
        if (position.x == 0 && position.y == 0) {
            return Direction.SOUTH_WEST
        }
        if (position.x == 0 && position.y == dimension.height - 1) {
            return Direction.NORTH_WEST
        }
        if (position.x == dimension.width -1 && position.y == 0) {
            return Direction.SOUTH_EAST
        }
        if (position.x == dimension.width -1 && position.y == dimension.height - 1) {
            return Direction.NORTH_EAST
        }

        // Then test for walls.
        if (position.x == 0) {
            return Direction.WEST
        }
        if (position.x == dimension.width -1) {
            return Direction.EAST
        }
        if (position.y == 0) {
            return Direction.SOUTH
        }
        if (position.y == dimension.height - 1) {
            return Direction.NORTH
        }

        return null
    }

    /**
     * Returns the position of the wall or corner that will be hit if we move by one step in the specified direction.
     */
    fun wallCollisionTest(dimension : Dimension, position: Point, direction : Direction) : Direction? {
        val wallOrCorner = wallHitTest(dimension, position) ?: return null
        if (wallOrCorner.isOrdinal()) {
            // Corner case.
            val commonDirection = direction.intersect(wallOrCorner)
            if (commonDirection != null) {
                // We are hitting the corner or the wall.
                // Ex: Corner: SW. Direction: SW, S, W, NW, SE.
                if (commonDirection == direction) {
                    // We are hitting the corner.
                    // Ex: Corner: SW. Direction: SW, S, W.
                    return wallOrCorner
                } else {
                    // We are hitting the wall.
                    // Ex: Corner: SW. Direction: NW (hitting W wall), SE (hitting E wall).
                    return commonDirection
                }
            }
            return null
        } else {
            // Wall case.
            // Ex: N Valid: N, NE, NW
            return direction.intersect(wallOrCorner)
        }
    }

    /**
     * Returns a new instance of a point which is the specified point translated by the given values.
     */
    fun movePoint(point : Point, x: Int, y: Int) : Point {
        return Point(point.x + x, point.y + y)
    }
}