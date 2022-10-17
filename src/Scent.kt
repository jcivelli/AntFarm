data class Scent private constructor(val id: Int, val type : Type, val intensity : Int) {
    enum class Type {
        FORAGE,
        RETURNING
    }
    companion object {
        val NO_SCENT = Scent(-1, Type.FORAGE, 0)
        const val MAX_INTENSITY = 100
    }

    constructor(id: Int = -1) : this(id, Type.FORAGE, MAX_INTENSITY)

    fun isDepleted() : Boolean {
        return intensity == 0
    }

    /** Returns a copy of this scent but with a reduced strength, or NO_SCENT if this scent has no more strength. */
    fun degrade() : Scent {
        if (intensity <= 1) return NO_SCENT
        return Scent(id, type, intensity - 1)
    }
}

typealias ScentMap = Array<Array<MutableList<Scent>>>

fun createEmptyScentMap(width : Int, height: Int) : ScentMap {
    return Array(width) { Array(height) { mutableListOf() } }
}
