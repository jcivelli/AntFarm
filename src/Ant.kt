import java.awt.Point
import java.lang.IllegalStateException

class Ant(val id: Int = -1, private val terrainModel: TerrainModel) {
    interface PositionListener {
        fun positionChanged(oldPosition: Point, newPosition: Point)
        fun directionChanged(oldDirection: Direction, newDirection: Direction)
    }

    data class PositionDirection(val position: Point, val direction: Direction)

    var position: Point = Point()
    var direction: Direction = Direction.NORTH
    private val previousPositions: MutableList<PositionDirection> = ArrayList()

    /**
     * Moves this ant by the specified number of steps. The ant's direction may change if the ant hits a wall.
     */
    fun move(numOfSteps: Int, coinFlipper: CoinFlip.Flipper, positionChangeListener: PositionListener? = null) {
        var steps = numOfSteps
        while (steps-- > 0) {
            moveByOne(coinFlipper)
            if (positionChangeListener != null) {
                val previousPosition = previousPositions.last()
                if (previousPosition.direction != direction) {
                    positionChangeListener.directionChanged(previousPosition.direction, direction)
                }
                positionChangeListener.positionChanged(previousPosition.position, position)
               }
        }
    }

    /**
     * Moves the ant by one square in its current direction. If it hits a wall changes direction randomly and returns
     * the new direction.
     */
    private fun moveByOne(coinFlipper: CoinFlip.Flipper) {
        previousPositions.add(PositionDirection(position, direction))

        // Change the direction if we hit a wall.
        reevaluateDirection(coinFlipper)

        // Then advance 1 step.
        val newX = if (direction.isEastBound()) {
            position.x + 1
        } else if (direction.isWestBound()) {
            position.x - 1
        } else {
            position.x
        }

        val newY = if (direction.isNorthBound()) {
            position.y + 1
        } else if (direction.isSouthBound()) {
            position.y - 1
        } else {
            position.y
        }
        position = Point(newX, newY)
    }

    /*
     * Changes the direction of the ant if it's hitting a wall.
     * The ant new direction is based on its previous direction, which wall/corner it's hitting and randomness:
     * - when hitting a wall at a 90 degree angle, it will go following one side of the wall picked randomly (ex: going
     *   N hits the N wall, will end up going E or W)
     * - when hitting a wall at an angle, it will either follow the side of the wall or continue at an angle away from
     *   the wall (ex: going NE hitting N wall, will go either E or SE)
     * - when hitting a corner, if it was already following a wall, it continues against the other wall (ex: going N
     *   hitting NW corner, will go E), if coming at an angle, it will continue following any of the 2 walls (ex: going
     *   SE hitting SE corner, will go either N or W)
     */
    private fun reevaluateDirection(coinFlipper: CoinFlip.Flipper) {
        val cornerOrWallHit = LocationUtils.wallCollisionTest(terrainModel.dimensions, position, direction) ?: return

        // Deal with corner cases first.
        if (cornerOrWallHit.isOrdinal()) {  // It's a corner.
            if (direction.isCardinal()) {
                // We are hitting at a 90-degree angle, walking along the wall.
                // Follow the other wall in the opposite direction.
                // Ex: going N, hitting NW corner -> going E
                // _____          ______
                // |^      ->     |>
                // |              |
                direction = cornerOrWallHit.differenceCardinalOrdinal(direction)?.getOpposite()
                        ?: throw IllegalStateException()
            } else {
                // We are hitting the corner at a 45-degree angle.
                // Follow any of the wall.
                // Ex: going NW, hitting N wall -> either go SW or W
                // -------               -------          -------
                // | \          ->       | ->        or   ||
                // |                     |                |v
                val candidateDirections = cornerOrWallHit.getCardinalComponents()
                direction = (if (coinFlipper.toss() == CoinFlip.HEAD) candidateDirections!!.first else candidateDirections!!.second).getOpposite()
            }
            return
        }

        // Wall case.
        if (direction.isCardinal()) {
            // We are hitting the wall at a 90-degree angle.
            // Ex: going N, hitting N wall -> either go E or go W
            // ------                ------          ------
            //   ^         ->           <        or     >
            val candidateDirections = direction.getOtherAxisDirections()
            direction = if (coinFlipper.toss() == CoinFlip.HEAD) candidateDirections!!.first else candidateDirections!!.second
            return
        } else {
            // We are hitting the wall at a 45-degree angle.
            // Either bounce back at a 45-degree or follow the wall.
            // Ex: going NW, hitting N wall -> either go SW or W
            // -------               -------          -------
            //    \          ->        /        or       <
            val axis = Direction.Axis.getAxis(cornerOrWallHit)!!.getOpposite()
            direction = if (coinFlipper.toss() == CoinFlip.HEAD) direction.differenceCardinalOrdinal(cornerOrWallHit) ?: throw IllegalStateException() else direction.getOrdinalReflection(axis)
        }
    }
}
