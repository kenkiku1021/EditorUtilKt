package jp.co.hamajima.editorutil

const val MM_PER_INCH = 25.4
const val PT_PER_INCH = 72.0
const val MM_PER_Q = 0.25

enum class FontSizeUnit {
    PT,
    Q
}

data class EULength(var mmValue: Double = 0.0) {
    companion object {
        fun fromMm(mm: Double) = EULength(mm)
        fun fromInches(inches: Double) = EULength(inches * MM_PER_INCH)
        fun fromPt(pt: Double) = EULength(pt * MM_PER_INCH / PT_PER_INCH)
        fun fromQ(q: Double) = EULength(q * MM_PER_Q)
    }

    var lengthInMm: Double
        get() = mmValue
        set(value) {
            mmValue = value
        }

    var lengthInInches: Double
        get() = mmValue / MM_PER_INCH
        set(value) {
            mmValue = value * MM_PER_INCH
        }

    var lengthInPt: Double
        get() = mmValue * PT_PER_INCH / MM_PER_INCH
        set(value) {
            mmValue = value * MM_PER_INCH / PT_PER_INCH
        }

    var lengthInQ: Double
        get() = mmValue / MM_PER_Q
        set(value) {
            mmValue = value * MM_PER_Q
        }
}