package jp.co.hamajima.editorutil

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

const val DEFAULT_PPI = 350

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun EUImageSizeView() {
    val initialPpi = settings.getInt("ppi", DEFAULT_PPI)
    var ppiStr by remember { mutableStateOf(initialPpi.toString()) }
    var imageSize: EUImageSize by remember { mutableStateOf(EUImageSize.fromPxAndPpi(0, 0, initialPpi)) }
    var image: ImageBitmap? by remember { mutableStateOf<ImageBitmap?>(null) }
    // 画像を表示するオフセット位置
    var imageOffset by remember { mutableStateOf(IntOffset(0, 0)) }
    var showDialog by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            Row (
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFE0E0E0)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // 画像読み込みボタンを追加
                    Button(onClick = {
                        // ファイルダイアログで画像を選択
                        try {
                            image = EUImageLoader()
                            val width = image?.width ?: 0
                            val height = image?.height ?: 0
                            imageSize = EUImageSize.fromPxAndPpi(width, height, imageSize.ppi)
                        } catch (e: Exception) {
                            alertMessage = e.message.toString()
                            showDialog = true
                        }
                    }) {
                        Text("画像読み込み")
                    }
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    TabFocusTextField(
                        singleLine = true,
                        label = {Text("解像度")},
                        value = ppiStr,
                        onValueChange = {
                            val value = it.trim().toIntOrNull()
                            if(value != null && value > 0) {
                                imageSize = EUImageSize.fromPxAndPpi(
                                    imageSize.width,
                                    imageSize.height,
                                    value
                                )
                                settings.putInt("ppi", value)
                            }
                            ppiStr = it
                        },
                        suffix = { Text("ppi") },
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFFE0E0E0)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("幅: ${imageSize.width} px")
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("高さ: ${imageSize.height} px")
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("幅: ${"%.0f".format(imageSize.widthLen.lengthInMm)} mm")
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("高さ: ${"%.0f".format(imageSize.heightLen.lengthInMm)} mm")
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 16.dp)
            ) {
                image?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier.offset {
                            // 画像のオフセット位置を設定
                            imageOffset
                        }
                    )
                }
            }
        }

        if(showDialog) {
            AlertDialog(
                onDismissRequest = {showDialog = false},
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("エラー") },
                text = { Text(alertMessage) }
            )
        }
    }
}