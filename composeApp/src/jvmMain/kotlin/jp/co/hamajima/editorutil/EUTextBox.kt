package jp.co.hamajima.editorutil

data class EUTextBox(val fontSize: EULength, val lineHeight: EULength, val cols: Int, val rows: Int, val width: EULength, val height: EULength) {
    override fun toString(): String {
        return "EUTextBox(fontSize=$fontSize, cols=$cols, rows=$rows, width=${formatLength(width.lengthInMm)}mm, height=${formatLength(height.lengthInMm)}mm)"
    }

    companion object {
        fun fromColsAndRows(fontSize: EULength, lineHeight: EULength, cols: Int, rows: Int): EUTextBox {
            val width = EULength.fromMm(fontSize.mmValue * cols)
            val height = EULength.fromMm(lineHeight.mmValue * (rows - 1) + fontSize.mmValue)
            return EUTextBox(fontSize, lineHeight, cols, rows, width, height)
        }
        fun fromWidthAndHeight(fontSize: EULength, lineHeight: EULength, width: EULength, height: EULength): `EUTextBox` {
            val cols = (width.mmValue / fontSize.mmValue).toInt()
            val rows = ((height.mmValue - fontSize.mmValue) / lineHeight.mmValue).toInt() + 1
            return EUTextBox(fontSize, lineHeight, cols, rows, width, height)
        }
    }
}