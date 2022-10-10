import com.google.common.collect.ImmutableList
import org.junit.Test

import com.google.common.truth.Truth
import org.junit.Assert
import java.lang.IllegalArgumentException

internal class DirectionTest {
    @Test
    fun isNorthBound() {
        Truth.assertThat(Direction.NORTH.isNorthBound()).isTrue()
        Truth.assertThat(Direction.NORTH_WEST.isNorthBound()).isTrue()
        Truth.assertThat(Direction.WEST.isNorthBound()).isFalse()
        Truth.assertThat(Direction.SOUTH_WEST.isNorthBound()).isFalse()
        Truth.assertThat(Direction.SOUTH.isNorthBound()).isFalse()
        Truth.assertThat(Direction.SOUTH_EAST.isNorthBound()).isFalse()
        Truth.assertThat(Direction.EAST.isNorthBound()).isFalse()
        Truth.assertThat(Direction.NORTH_EAST.isNorthBound()).isTrue()
    }

    @Test
    fun isWestBound() {
        Truth.assertThat(Direction.NORTH.isWestBound()).isFalse()
        Truth.assertThat(Direction.NORTH_WEST.isWestBound()).isTrue()
        Truth.assertThat(Direction.WEST.isWestBound()).isTrue()
        Truth.assertThat(Direction.SOUTH_WEST.isWestBound()).isTrue()
        Truth.assertThat(Direction.SOUTH.isWestBound()).isFalse()
        Truth.assertThat(Direction.SOUTH_EAST.isWestBound()).isFalse()
        Truth.assertThat(Direction.EAST.isWestBound()).isFalse()
        Truth.assertThat(Direction.NORTH_EAST.isWestBound()).isFalse()
    }

    @Test
    fun isSouthBound() {
        Truth.assertThat(Direction.NORTH.isSouthBound()).isFalse()
        Truth.assertThat(Direction.NORTH_WEST.isSouthBound()).isFalse()
        Truth.assertThat(Direction.WEST.isSouthBound()).isFalse()
        Truth.assertThat(Direction.SOUTH_WEST.isSouthBound()).isTrue()
        Truth.assertThat(Direction.SOUTH.isSouthBound()).isTrue()
        Truth.assertThat(Direction.SOUTH_EAST.isSouthBound()).isTrue()
        Truth.assertThat(Direction.EAST.isSouthBound()).isFalse()
        Truth.assertThat(Direction.NORTH_EAST.isSouthBound()).isFalse()
    }

    @Test
    fun isEastBound() {
        Truth.assertThat(Direction.NORTH.isEastBound()).isFalse()
        Truth.assertThat(Direction.NORTH_WEST.isEastBound()).isFalse()
        Truth.assertThat(Direction.WEST.isEastBound()).isFalse()
        Truth.assertThat(Direction.SOUTH_WEST.isEastBound()).isFalse()
        Truth.assertThat(Direction.SOUTH.isEastBound()).isFalse()
        Truth.assertThat(Direction.SOUTH_EAST.isEastBound()).isTrue()
        Truth.assertThat(Direction.EAST.isEastBound()).isTrue()
        Truth.assertThat(Direction.NORTH_EAST.isEastBound()).isTrue()
    }

    @Test
    fun isCardinal() {
        Truth.assertThat(Direction.NORTH.isCardinal()).isTrue()
        Truth.assertThat(Direction.EAST.isCardinal()).isTrue()
        Truth.assertThat(Direction.SOUTH.isCardinal()).isTrue()
        Truth.assertThat(Direction.WEST.isCardinal()).isTrue()
        Truth.assertThat(Direction.NORTH_WEST.isCardinal()).isFalse()
        Truth.assertThat(Direction.NORTH_EAST.isCardinal()).isFalse()
        Truth.assertThat(Direction.SOUTH_EAST.isCardinal()).isFalse()
        Truth.assertThat(Direction.SOUTH_EAST.isCardinal()).isFalse()
    }

    @Test
    fun isOrdinal() {
        Truth.assertThat(Direction.NORTH.isOrdinal()).isFalse()
        Truth.assertThat(Direction.EAST.isOrdinal()).isFalse()
        Truth.assertThat(Direction.SOUTH.isOrdinal()).isFalse()
        Truth.assertThat(Direction.WEST.isOrdinal()).isFalse()
        Truth.assertThat(Direction.NORTH_WEST.isOrdinal()).isTrue()
        Truth.assertThat(Direction.NORTH_EAST.isOrdinal()).isTrue()
        Truth.assertThat(Direction.SOUTH_EAST.isOrdinal()).isTrue()
        Truth.assertThat(Direction.SOUTH_WEST.isOrdinal()).isTrue()
    }

    @Test
    fun getCardinalComponents() {
        Truth.assertThat(Direction.NORTH.getCardinalComponents()).isNull()
        Truth.assertThat(Direction.EAST.getCardinalComponents()).isNull()
        Truth.assertThat(Direction.SOUTH.getCardinalComponents()).isNull()
        Truth.assertThat(Direction.WEST.getCardinalComponents()).isNull()
        Truth.assertThat(Direction.NORTH_WEST.getCardinalComponents()).isEqualTo(Pair(Direction.NORTH, Direction.WEST))
        Truth.assertThat(Direction.NORTH_EAST.getCardinalComponents()).isEqualTo(Pair(Direction.NORTH, Direction.EAST))
        Truth.assertThat(Direction.SOUTH_EAST.getCardinalComponents()).isEqualTo(Pair(Direction.SOUTH, Direction.EAST))
        Truth.assertThat(Direction.SOUTH_WEST.getCardinalComponents()).isEqualTo(Pair(Direction.SOUTH, Direction.WEST))
    }

    @Test
    fun getAdjacentDirections_paramsCheck() {
        Assert.assertThrows(IllegalArgumentException::class.java) { Direction.NORTH.getAdjacentDirections(-1) }
        Assert.assertThrows(IllegalArgumentException::class.java) { Direction.NORTH.getAdjacentDirections(4) }
    }

    @Test
    fun getAdjacentDirections_north() {
        Truth.assertThat(Direction.NORTH.getAdjacentDirections(0)).isEqualTo(ImmutableList.of(Direction.NORTH))
        Truth.assertThat(Direction.NORTH.getAdjacentDirections(1)).isEqualTo(
            ImmutableList.of(Direction.NORTH_WEST, Direction.NORTH, Direction.NORTH_EAST)
        )
        Truth.assertThat(Direction.NORTH.getAdjacentDirections(2)).isEqualTo(
            ImmutableList.of(
                Direction.WEST,
                Direction.NORTH_WEST,
                Direction.NORTH,
                Direction.NORTH_EAST,
                Direction.EAST
            )
        )
        Truth.assertThat(Direction.NORTH.getAdjacentDirections(3)).isEqualTo(
            ImmutableList.of(
                Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST, Direction.NORTH,
                Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST
            )
        )
    }

    @Test
    fun getAdjacentDirections_southEast() {
        Truth.assertThat(Direction.SOUTH_EAST.getAdjacentDirections(0))
            .isEqualTo(ImmutableList.of(Direction.SOUTH_EAST))
        Truth.assertThat(Direction.SOUTH_EAST.getAdjacentDirections(1)).isEqualTo(
            ImmutableList.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH)
        )
        Truth.assertThat(Direction.SOUTH_EAST.getAdjacentDirections(2)).isEqualTo(
            ImmutableList.of(
                Direction.NORTH_EAST,
                Direction.EAST,
                Direction.SOUTH_EAST,
                Direction.SOUTH,
                Direction.SOUTH_WEST
            )
        )
        Truth.assertThat(Direction.SOUTH_EAST.getAdjacentDirections(3)).isEqualTo(
            ImmutableList.of(
                Direction.NORTH,
                Direction.NORTH_EAST,
                Direction.EAST,
                Direction.SOUTH_EAST,
                Direction.SOUTH,
                Direction.SOUTH_WEST,
                Direction.WEST
            )
        )
    }

    @Test
    fun getAdjacentDirections_NorthWest() {
        Truth.assertThat(Direction.NORTH_WEST.getAdjacentDirections(0)).isEqualTo(
            ImmutableList.of(Direction.NORTH_WEST)
        )
        Truth.assertThat(Direction.NORTH_WEST.getAdjacentDirections(1)).isEqualTo(
            ImmutableList.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH)
        )
        Truth.assertThat(Direction.NORTH_WEST.getAdjacentDirections(2)).isEqualTo(
            ImmutableList.of(
                Direction.SOUTH_WEST,
                Direction.WEST,
                Direction.NORTH_WEST,
                Direction.NORTH,
                Direction.NORTH_EAST
            )
        )
        Truth.assertThat(Direction.NORTH_WEST.getAdjacentDirections(3)).isEqualTo(
            ImmutableList.of(
                Direction.SOUTH,
                Direction.SOUTH_WEST,
                Direction.WEST,
                Direction.NORTH_WEST,
                Direction.NORTH,
                Direction.NORTH_EAST,
                Direction.EAST
            )
        )
    }

    @Test
    fun getOtherAxisDirections() {
        Truth.assertThat(Direction.NORTH.getOtherAxisDirections()).isEqualTo(Pair(Direction.EAST, Direction.WEST))
        Truth.assertThat(Direction.SOUTH.getOtherAxisDirections()).isEqualTo(Pair(Direction.EAST, Direction.WEST))
        Truth.assertThat(Direction.EAST.getOtherAxisDirections()).isEqualTo(Pair(Direction.NORTH, Direction.SOUTH))
        Truth.assertThat(Direction.WEST.getOtherAxisDirections()).isEqualTo(Pair(Direction.NORTH, Direction.SOUTH))
        Truth.assertThat(Direction.NORTH_EAST.getOtherAxisDirections()).isNull()
        Truth.assertThat(Direction.SOUTH_EAST.getOtherAxisDirections()).isNull()
        Truth.assertThat(Direction.SOUTH_WEST.getOtherAxisDirections()).isNull()
        Truth.assertThat(Direction.NORTH_WEST.getOtherAxisDirections()).isNull()
    }

    @Test
    fun axis_getOpposite() {
        Truth.assertThat(Direction.Axis.VERTICAL.getOpposite()).isEqualTo(Direction.Axis.HORIZONTAL)
        Truth.assertThat(Direction.Axis.HORIZONTAL.getOpposite()).isEqualTo(Direction.Axis.VERTICAL)
    }

    @Test
    fun intersect_identity() {
        Truth.assertThat(Direction.NORTH.intersect(Direction.NORTH)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.NORTH_EAST.intersect(Direction.NORTH_EAST)).isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(Direction.EAST.intersect(Direction.EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.SOUTH_EAST.intersect(Direction.SOUTH_EAST)).isEqualTo(Direction.SOUTH_EAST)
        Truth.assertThat(Direction.SOUTH.intersect(Direction.SOUTH)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH_WEST.intersect(Direction.SOUTH_WEST)).isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(Direction.WEST.intersect(Direction.WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.NORTH_WEST.intersect(Direction.NORTH_WEST)).isEqualTo(Direction.NORTH_WEST)
    }

    @Test
    fun intersect_north() {
        Truth.assertThat(Direction.NORTH.intersect(Direction.NORTH_EAST)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.NORTH.intersect(Direction.NORTH_WEST)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.NORTH_EAST.intersect(Direction.NORTH)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.NORTH_WEST.intersect(Direction.NORTH)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.NORTH.intersect(Direction.WEST)).isNull()
        Truth.assertThat(Direction.NORTH.intersect(Direction.SOUTH)).isNull()
        Truth.assertThat(Direction.NORTH.intersect(Direction.SOUTH_EAST)).isNull()
    }

    @Test
    fun intersect_west() {
        Truth.assertThat(Direction.WEST.intersect(Direction.NORTH_WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.WEST.intersect(Direction.SOUTH_WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.NORTH_WEST.intersect(Direction.WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.SOUTH_WEST.intersect(Direction.WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.WEST.intersect(Direction.EAST)).isNull()
        Truth.assertThat(Direction.WEST.intersect(Direction.SOUTH)).isNull()
        Truth.assertThat(Direction.WEST.intersect(Direction.NORTH_EAST)).isNull()
    }

    @Test
    fun intersect_south() {
        Truth.assertThat(Direction.SOUTH.intersect(Direction.SOUTH_EAST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH.intersect(Direction.SOUTH_WEST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH_EAST.intersect(Direction.SOUTH)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH_WEST.intersect(Direction.SOUTH)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH.intersect(Direction.WEST)).isNull()
        Truth.assertThat(Direction.SOUTH.intersect(Direction.NORTH)).isNull()
        Truth.assertThat(Direction.SOUTH.intersect(Direction.NORTH_EAST)).isNull()
    }

    @Test
    fun intersect_east() {
        Truth.assertThat(Direction.EAST.intersect(Direction.NORTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.EAST.intersect(Direction.SOUTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.NORTH_EAST.intersect(Direction.EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.SOUTH_EAST.intersect(Direction.EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.EAST.intersect(Direction.WEST)).isNull()
        Truth.assertThat(Direction.EAST.intersect(Direction.SOUTH)).isNull()
        Truth.assertThat(Direction.EAST.intersect(Direction.NORTH_WEST)).isNull()
    }

    @Test
    fun opposite() {
        Truth.assertThat(Direction.NORTH.getOpposite()).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.NORTH_EAST.getOpposite()).isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(Direction.EAST.getOpposite()).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.SOUTH_EAST.getOpposite()).isEqualTo(Direction.NORTH_WEST)
        Truth.assertThat(Direction.SOUTH.getOpposite()).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.SOUTH_WEST.getOpposite()).isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(Direction.WEST.getOpposite()).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.NORTH_WEST.getOpposite()).isEqualTo(Direction.SOUTH_EAST)
    }

    @Test
    fun differenceCardinalOrdinal() {
        Truth.assertThat(Direction.NORTH.differenceCardinalOrdinal(Direction.NORTH)).isNull()
        Truth.assertThat(Direction.NORTH.differenceCardinalOrdinal(Direction.SOUTH)).isNull()
        Truth.assertThat(Direction.NORTH.differenceCardinalOrdinal(Direction.EAST)).isNull()

        Truth.assertThat(Direction.NORTH.differenceCardinalOrdinal(Direction.NORTH_EAST)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.NORTH_EAST.differenceCardinalOrdinal(Direction.NORTH)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.NORTH.differenceCardinalOrdinal(Direction.NORTH_WEST)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.NORTH.differenceCardinalOrdinal(Direction.SOUTH_WEST)).isNull()

        Truth.assertThat(Direction.WEST.differenceCardinalOrdinal(Direction.SOUTH_WEST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH_WEST.differenceCardinalOrdinal(Direction.WEST)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.WEST.differenceCardinalOrdinal(Direction.NORTH_WEST)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.WEST.differenceCardinalOrdinal(Direction.SOUTH_EAST)).isNull()
    }

    @Test
    fun getOrdinalReflection_horizontal() {
        Truth.assertThat(Direction.NORTH.getOrdinalReflection(Direction.Axis.HORIZONTAL)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.NORTH_WEST.getOrdinalReflection(Direction.Axis.HORIZONTAL))
            .isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(Direction.WEST.getOrdinalReflection(Direction.Axis.HORIZONTAL)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.SOUTH_WEST.getOrdinalReflection(Direction.Axis.HORIZONTAL))
            .isEqualTo(Direction.NORTH_WEST)
        Truth.assertThat(Direction.SOUTH.getOrdinalReflection(Direction.Axis.HORIZONTAL)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.SOUTH_EAST.getOrdinalReflection(Direction.Axis.HORIZONTAL))
            .isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(Direction.EAST.getOrdinalReflection(Direction.Axis.HORIZONTAL)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.NORTH_EAST.getOrdinalReflection(Direction.Axis.HORIZONTAL))
            .isEqualTo(Direction.SOUTH_EAST)
    }

    @Test
    fun getOrdinalReflection_vertical() {
        Truth.assertThat(Direction.NORTH.getOrdinalReflection(Direction.Axis.VERTICAL)).isEqualTo(Direction.NORTH)
        Truth.assertThat(Direction.NORTH_WEST.getOrdinalReflection(Direction.Axis.VERTICAL))
            .isEqualTo(Direction.NORTH_EAST)
        Truth.assertThat(Direction.WEST.getOrdinalReflection(Direction.Axis.VERTICAL)).isEqualTo(Direction.EAST)
        Truth.assertThat(Direction.SOUTH_WEST.getOrdinalReflection(Direction.Axis.VERTICAL))
            .isEqualTo(Direction.SOUTH_EAST)
        Truth.assertThat(Direction.SOUTH.getOrdinalReflection(Direction.Axis.VERTICAL)).isEqualTo(Direction.SOUTH)
        Truth.assertThat(Direction.SOUTH_EAST.getOrdinalReflection(Direction.Axis.VERTICAL))
            .isEqualTo(Direction.SOUTH_WEST)
        Truth.assertThat(Direction.EAST.getOrdinalReflection(Direction.Axis.VERTICAL)).isEqualTo(Direction.WEST)
        Truth.assertThat(Direction.NORTH_EAST.getOrdinalReflection(Direction.Axis.VERTICAL))
            .isEqualTo(Direction.NORTH_WEST)
    }
}