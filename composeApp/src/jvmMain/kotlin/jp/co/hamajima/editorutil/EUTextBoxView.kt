package jp.co.hamajima.editorutil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


const val DEFAULT_FONT_SIZE = 15.0
const val DEFAULT_LINE_HEIGHT = 24.0

@Composable
fun EUTextBoxView() {
    var fontSizeUnit by remember { mutableStateOf(FontSizeUnit.Q) }
    val initialFontSize = settings.getDouble("fontSize", DEFAULT_FONT_SIZE)
    var fontSizeStr by remember { mutableStateOf(formatLength(initialFontSize)) }
    val initialLineHeight = settings.getDouble("lineHeight", DEFAULT_LINE_HEIGHT)
    var lineHeightStr by remember { mutableStateOf(formatLength(initialLineHeight)) }
    val initialCols = settings.getInt("cols", 0)
    var colsStr by remember { mutableStateOf(initialCols.toString()) }
    val initialRows = settings.getInt("rows", 0)
    var rowsStr by remember { mutableStateOf(initialRows.toString()) }
    val initialWidth = settings.getDouble("width", 0.0)
    var widthStr by remember { mutableStateOf(initialWidth.toString()) }
    val initialHeight = settings.getDouble("height", 0.0)
    var heightStr by remember { mutableStateOf(initialHeight.toString()) }
    var box by remember { mutableStateOf(EUTextBox.fromColsAndRows(
        EULength.fromQ(initialFontSize),
        EULength.fromQ(initialHeight),
        initialCols,
        initialRows)) }

    val updateBoxStr = {
        settings.putDouble("fontSize", box.fontSize.lengthInQ)
        settings.putDouble("lineHeight", box.lineHeight.lengthInQ)
        settings.putInt("cols", box.cols)
        settings.putInt("rows", box.rows)
        settings.putDouble("width", box.width.lengthInMm)
        settings.putDouble("height", box.height.lengthInMm)
        fontSizeStr = formatLength(box.fontSize.lengthInQ)
        lineHeightStr = formatLength(box.lineHeight.lengthInQ)
        colsStr = box.cols.toString()
        rowsStr = box.rows.toString()
        widthStr = formatLength(box.width.lengthInMm)
        heightStr = formatLength(box.height.lengthInMm)
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(it)
                    .padding(all = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = { Text("級数") },
                        value = fontSizeStr,
                        onValueChange = {
                            val value = it.trim().toDoubleOrNull()
                            if (value != null) {
                                val length = when (fontSizeUnit) {
                                    FontSizeUnit.PT -> EULength.fromPt(value)
                                    FontSizeUnit.Q -> EULength.fromQ(value)
                                }
                                box = EUTextBox.fromColsAndRows(length, box.lineHeight, box.cols, box.rows)
                                updateBoxStr()
                            }
                            fontSizeStr = it
                        },
                        suffix = {
                            when (fontSizeUnit) {
                                FontSizeUnit.Q -> {
                                    Text("Q")
                                }

                                FontSizeUnit.PT -> {
                                    Text("pt")
                                }
                            }
                        }
                    )
                }
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = { Text("行送り") },
                        value = lineHeightStr,
                        onValueChange = {
                            val value = it.trim().toDoubleOrNull()
                            if (value != null) {
                                val length = when (fontSizeUnit) {
                                    FontSizeUnit.PT -> EULength.fromPt(value)
                                    FontSizeUnit.Q -> EULength.fromQ(value)
                                }
                                box = EUTextBox.fromColsAndRows(box.fontSize, length, box.cols, box.rows)
                                updateBoxStr()
                            }
                            lineHeightStr = it
                        },
                        suffix = {
                            when (fontSizeUnit) {
                                FontSizeUnit.Q -> {
                                    Text("H")
                                }

                                FontSizeUnit.PT -> {
                                    Text("pt")
                                }
                            }
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(it)
                    .padding(all = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = { Text("文字数") },
                        value = colsStr,
                        onValueChange = {
                            val value = it.trim().toIntOrNull()
                            if (value != null) {
                                box = EUTextBox.fromColsAndRows(box.fontSize, box.lineHeight, value, box.rows)
                                updateBoxStr()
                            }
                            colsStr = it
                        },
                        suffix = { Text("字") }
                    )
                }
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = { Text("行数") },
                        value = rowsStr,
                        onValueChange = {
                            val value = it.trim().toIntOrNull()
                            if (value != null) {
                                box = EUTextBox.fromColsAndRows(box.fontSize, box.lineHeight, box.cols, value)
                                updateBoxStr()
                            }
                            rowsStr = it
                        },
                        suffix = { Text("行") }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(it)
                    .padding(all = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = { Text("幅") },
                        value = widthStr,
                        onValueChange = {
                            val value = it.trim().toDoubleOrNull()
                            if (value != null) {
                                box = EUTextBox.fromWidthAndHeight(box.fontSize, box.lineHeight, EULength.fromMm(value), box.height)
                                updateBoxStr()
                            }
                            widthStr = it
                        },
                        suffix = { Text("mm") }
                    )
                }
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = { Text("高さ") },
                        value = heightStr,
                        onValueChange = {
                            val value = it.trim().toDoubleOrNull()
                            if (value != null) {
                                box = EUTextBox.fromWidthAndHeight(box.fontSize, box.lineHeight, box.width, EULength.fromMm(value))
                                updateBoxStr()
                            }
                            heightStr = it
                        },
                        suffix = { Text("mm") }
                    )
                }
            }
        }
    }
}