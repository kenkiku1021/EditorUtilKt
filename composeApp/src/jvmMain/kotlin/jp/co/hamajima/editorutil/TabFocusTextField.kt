package jp.co.hamajima.editorutil

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

@Composable
fun TabFocusTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    singleLine: Boolean = false,
    textStyle: TextStyle = TextStyle.Default
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.onPreviewKeyEvent { keyEvent: KeyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Tab) {
                // Shift が押されていれば前に、そうでなければ次へフォーカス移動
                if (keyEvent.isShiftPressed) {
                    focusManager.moveFocus(FocusDirection.Previous)
                } else {
                    focusManager.moveFocus(FocusDirection.Next)
                }
                true // イベントを消費してテキストに Tab を挿入しない
            } else {
                false
            }
        },
        label = label,
        suffix = suffix,
        singleLine = singleLine,
        textStyle = textStyle
    )
}