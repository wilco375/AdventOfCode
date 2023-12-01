class Coord(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        return Pair(x, y).hashCode()
    }

    override fun toString(): String {
        return "(x=$x, y=$y)"
    }


}