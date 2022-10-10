/**
 * Describes the behavior of an ant should have when:
 * - travelling (straight line, direction change)
 * - hitting a wall/corner
 * - finding food
 * - stepping on an ant's trace
 *
 */
interface AntBehavior {
    /**
     * The number of steps an ant will travel in a straight line before reevaluating.
     */
    fun getTravelSegmentLength() : Int {
        return 3
    }

    /**
     * Given a direction, returns a list of direction that could take (assuming there are no wall/corner).
     * (the caller typically picks randomly from the potential directions.
     */
    fun getPotentialDirectionChange(direction :Direction) : List<Direction> {
        return direction.getAdjacentDirections(1)
    }
}

/**
 * An implementation of the behavior where we go in a straight line without reevaluating the direction.
 */
class StraightLineAntBehavior : AntBehavior {
    override fun getTravelSegmentLength() : Int {
        return 0
    }

    override fun getPotentialDirectionChange(direction :Direction) : List<Direction> {
        return listOf(direction)
    }
}
