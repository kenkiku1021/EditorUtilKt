package jp.co.hamajima.editorutil

fun formatLength(length: Double): String {
    return if (length % 1.0 == 0.0) {
        length.toInt().toString()
    } else {
        String.format("%.2f", length)
    }
}
