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
}