package jp.co.hamajima.editorutil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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


@Composable
fun EULengthConverterView() {
    val initialLength = EULength(settings.getDouble("length", 0.0))
    var mmLengthStr by remember { mutableStateOf(formatLength(initialLength.lengthInMm)) }
    var inchesLengthStr by remember { mutableStateOf( formatLength(initialLength.lengthInInches)) }
    var ptLengthStr by remember { mutableStateOf(formatLength(initialLength.lengthInPt)) }
    var qLengthStr by remember { mutableStateOf(formatLength(initialLength.lengthInQ)) }
    var length by remember { mutableStateOf(initialLength) }

    val updateLengthStr = {
        settings.putDouble("length", length.lengthInMm)
        mmLengthStr = formatLength(length.lengthInMm)
        inchesLengthStr = formatLength(length.lengthInInches)
        ptLengthStr = formatLength(length.lengthInPt)
        qLengthStr = formatLength(length.lengthInQ)
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it).padding(16.dp)
        ) {
            Row (
                modifier = Modifier.padding(bottom = 16.dp)
            ){
                TabFocusTextField(
                    singleLine = true,
                    value = mmLengthStr,
                    onValueChange = {
                        val value = it.trim().toDoubleOrNull()
                        if(value != null) {
                            length = EULength.fromMm(value)
                            updateLengthStr()
                        }
                        mmLengthStr = it
                    },
                    suffix = { Text("mm") }
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                TabFocusTextField(
                    singleLine = true,
                    value = inchesLengthStr,
                    onValueChange = {
                        val value = it.trim().toDoubleOrNull()
                        if(value != null) {
                            length = EULength.fromInches(value)
                            updateLengthStr()
                        }
                        inchesLengthStr = it
                    },
                    suffix = { Text("inches") }
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                TabFocusTextField(
                    singleLine = true,
                    value = ptLengthStr,
                    onValueChange = {
                        val value = it.trim().toDoubleOrNull()
                        if (value != null) {
                            length = EULength.fromPt(value)
                            updateLengthStr()
                        }
                        ptLengthStr = it
                    },
                    suffix = { Text("pt") }
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                TabFocusTextField(
                    singleLine = true,
                    value = qLengthStr,
                    onValueChange = {
                        val value = it.trim().toDoubleOrNull()
                        if (value != null) {
                            length = EULength.fromQ(value)
                            updateLengthStr()
                        }
                        qLengthStr = it
                    },
                    suffix = { Text("Q") }
                )
            }
        }
    }
}