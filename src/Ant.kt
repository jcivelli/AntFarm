import java.awt.Point
import java.lang.IllegalStateException
import kotlin.random.Random

class Ant(
    val id: Int = -1,
    private val terrainModel: TerrainModel,
    private val behavior: AntBehavior =  object : AntBehavior {}) {

    interface PositionListener {
        fun positionChanged(oldPosition: Point, newPosition: Point)
        fun directionChanged(oldDirection: Direction, newDirection: Direction)
    }

    data class PositionDirection(val position: Point, val direction: Direction)

    var position: Point = Point()
    var direction: Direction = Direction.NORTH

    private var moveSinceLastDirectionReEval = 0
    private val previousPositions: MutableList<PositionDirection> = ArrayList()

    /**
     * Moves the ant by one square in its current direction. If it hits a wall changes direction randomly and returns
     * the new direction.
     */
    fun move(random: Random, positionChangeListener: PositionListener? = null) {
        previousPositions.add(PositionDirection(position, direction))

        // Change the direction if we hit a wall or have gone in a straight line for too long.
        reevaluateDirection(random)

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

        if (positionChangeListener != null) {
            val previousPosition = previousPositions.last()
            if (previousPosition.direction != direction) {
                positionChangeListener.directionChanged(previousPosition.direction, direction)
            }
            positionChangeListener.positionChanged(previousPosition.position, position)
        }
    }

    fun markCell() : Scent {
        return Scent(id)
    }
    private fun reevaluateDirection(random: Random) {
        val directionBefore = direction

        if (!reevaluateDirectionForCollision(random)) {
            // We didn't hit a wall. Consider changing direction if we have been travelling in that direction for some
            // time.
            if (moveSinceLastDirectionReEval == behavior.getTravelSegmentLength()) {
                var directions = behavior.getPotentialDirectionChange(direction)
                val cornerOrWall = LocationUtils.wallHitTest(terrainModel.dimensions, position)
                if (cornerOrWall != null) {
                    directions = intersectDirections(cornerOrWall, directions)
                }
                direction = directions[random.nextInt(directions.size)]
                moveSinceLastDirectionReEval = 0
                return
            }
        }

        if (direction != directionBefore) {
            moveSinceLastDirectionReEval = 0
        } else {
            moveSinceLastDirectionReEval++
        }
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
     * @return true the direction changed false otherwise.
     */
    private fun reevaluateDirectionForCollision(random: Random): Boolean {
        val cornerOrWallHit =
            LocationUtils.wallCollisionTest(terrainModel.dimensions, position, direction) ?: return false

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
                direction =
                    (if (random.nextBoolean()) candidateDirections!!.first else candidateDirections!!.second).getOpposite()
            }
            return true
        }

        // Wall case.
        if (direction.isCardinal()) {
            // We are hitting the wall at a 90-degree angle.
            // Ex: going N, hitting N wall -> either go E or go W
            // ------                ------          ------
            //   ^         ->           <        or     >
            val candidateDirections = direction.getOtherAxisDirections()
            direction = if (random.nextBoolean()) candidateDirections!!.first else candidateDirections!!.second
            return true
        } else {
            // We are hitting the wall at a 45-degree angle.
            // Either bounce back at a 45-degree or follow the wall.
            // Ex: going NW, hitting N wall -> either go SW or W
            // -------               -------          -------
            //    \          ->        /        or       <
            val axis = Direction.Axis.getAxis(cornerOrWallHit)!!.getOpposite()
            direction = if (random.nextBoolean()) direction.differenceCardinalOrdinal(cornerOrWallHit)
                ?: throw IllegalStateException() else direction.getOrdinalReflection(axis)
            return true
        }
    }

    private fun intersectDirections(direction: Direction, otherDirections: List<Direction>)
        : List<Direction> {
        return otherDirections.filter {
            direction.intersect(it) == null
        }
    }
}
