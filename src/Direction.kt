
enum class Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    fun isNorthBound() : Boolean {
        return this == NORTH || this == NORTH_WEST || this == NORTH_EAST
    }

    fun isWestBound() : Boolean {
        return this == WEST || this == NORTH_WEST || this == SOUTH_WEST
    }

    fun isSouthBound() : Boolean {
        return this == SOUTH || this == SOUTH_WEST || this == SOUTH_EAST
    }

    fun isEastBound() : Boolean {
        return this == EAST || this == SOUTH_EAST || this == NORTH_EAST
    }

    /**
     * Cardinal directions are N, E, S, W.
     */
    fun isCardinal() : Boolean {
        return this == NORTH || this == EAST || this == SOUTH || this == WEST
    }

    /**
     * Ordinal directions are: NW, NE, SE, SW.
     */
    fun isOrdinal() : Boolean {
        return this == NORTH_WEST || this == NORTH_EAST || this == SOUTH_EAST || this == SOUTH_WEST
    }

    /**
     * Returns the 2 cardinal components of this direction if it's ordinal, null otherwise.
     * Ex: NW -> N & W
     */
    fun getCardinalComponents() : Pair<Direction, Direction>? {
        if (this == NORTH_EAST) return Pair(NORTH, EAST)
        if (this == NORTH_WEST) return Pair(NORTH, WEST)
        if (this == SOUTH_WEST) return Pair(SOUTH, WEST)
        if (this == SOUTH_EAST) return Pair(SOUTH, EAST)
        return null
    }

    /**
     * Returns the 2 cardinal directions on the axis that is not on this direction axis, or null is this is not a
     * cardinal direction.
     */
    fun getOtherAxisDirections() : Pair<Direction, Direction>? {
        if (!this.isCardinal()) return null
        return if (this == SOUTH || this == NORTH) Pair(EAST, WEST) else Pair(NORTH, SOUTH)
    }
    /**
     * Returns the direction that is the intersection of this direction and the passed in one, or null if there are no
     * common ones.
     */
    fun intersect(other : Direction) : Direction? {
        if (this == other) {
            return this
        }
        if (isNorthBound() && other.isNorthBound()) {
            return NORTH
        }
        if (isEastBound() && other.isEastBound()) {
            return EAST
        }
        if (isSouthBound() && other.isSouthBound()) {
            return SOUTH
        }
        if (isWestBound() && other.isWestBound()) {
            return WEST
        }
        return null
    }

    /**
     * Returns the difference between a cardinal and an ordinal direction, returning the cardinal direction not common
     * to both. (ex: NE & E -> N, SW & S -> W)
     * Returns null if the 2 directions have no cardinal direction in common.
     */
    fun differenceCardinalOrdinal(other: Direction) : Direction? {
        if (!(isCardinal() && other.isOrdinal()) && !(isOrdinal() && other.isCardinal())) {
            return null
        }

        val ordinal = if (isOrdinal()) this else other
        if ((isNorthBound() && other.isNorthBound()) || ((isSouthBound() && other.isSouthBound()))) {
            return if (ordinal.isEastBound()) EAST else WEST
        }
        if ((isWestBound() && other.isWestBound()) || (isEastBound() && other.isEastBound())) {
            return if (ordinal.isSouthBound()) SOUTH else NORTH
        }
        return null
    }

    fun getOpposite() : Direction {
        if (this == NORTH) return SOUTH
        if (this == NORTH_EAST) return SOUTH_WEST
        if (this == EAST) return WEST
        if (this == SOUTH_EAST) return NORTH_WEST
        if (this == SOUTH) return NORTH
        if (this == SOUTH_WEST) return NORTH_EAST
        if (this == WEST) return EAST
        // NORTH_WEST.
        return SOUTH_EAST
    }

    enum class Axis {
        VERTICAL, HORIZONTAL;

        companion object {
            fun getAxis(direction : Direction) : Axis? {
                if (direction == NORTH || direction == SOUTH) return VERTICAL
                if (direction == WEST || direction == EAST) return HORIZONTAL
                // Ordinals don't have an axis.
                return null
            }
        }

        fun getOpposite() : Axis {
            return if (this == VERTICAL) HORIZONTAL else VERTICAL
        }
    }

    /**
     * Returns the direction resulting from a reflectional symmetry of this direction.
     */
    fun getOrdinalReflection(axis : Axis) : Direction {
        if (axis == Axis.VERTICAL) {
            if (this == NORTH) return NORTH
            if (this == NORTH_EAST) return NORTH_WEST
            if (this == EAST) return WEST
            if (this == SOUTH_EAST) return SOUTH_WEST
            if (this == SOUTH) return SOUTH
            if (this == SOUTH_WEST) return SOUTH_EAST
            if (this == WEST) return EAST
            if (this == NORTH_WEST) return NORTH_EAST
            assert(false)
        }
        assert(axis == Axis.HORIZONTAL)
        if (this == NORTH) return SOUTH
        if (this == NORTH_EAST) return SOUTH_EAST
        if (this == EAST) return EAST
        if (this == SOUTH_EAST) return NORTH_EAST
        if (this == SOUTH) return NORTH
        if (this == SOUTH_WEST) return NORTH_WEST
        if (this == WEST) return WEST
        assert(this == NORTH_WEST)
        return SOUTH_WEST
    }
}
