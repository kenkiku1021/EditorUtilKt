package jp.co.hamajima.editorutil

data class EUImageSize(val width: Int, val height: Int, val ppi: Int, val widthLen: EULength, val heightLen: EULength) {
    override fun toString(): String {
        return "EUImageSize(width=$width, height=$height, ppi=$ppi)"
    }

    companion object {
        fun fromPxAndPpi(width: Int, height: Int, ppi: Int): EUImageSize {
            val widthInches = width.toFloat() / ppi.toDouble()
            val heightInches = height.toFloat() / ppi.toDouble()
            val widthLen = EULength.fromInches(widthInches)
            val heightLen = EULength.fromInches(heightInches)
            return EUImageSize(width, height, ppi, widthLen, heightLen)
        }
    }
}