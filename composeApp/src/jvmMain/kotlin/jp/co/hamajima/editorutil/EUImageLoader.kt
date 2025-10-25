package jp.co.hamajima.editorutil

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter


// ファイル選択ダイアログを開き、画像を読み込むための関数
fun EUImageLoader(): ImageBitmap { // JVM-specific image loading implementation
    // ファイル選択ダイアログを作成
    val chooser = JFileChooser().apply {
        fileFilter = FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "tif", "psd", "webp")
        dialogTitle = "画像を選択"
        // 初期ディレクトリをユーザーのホームに設定（任意）
        currentDirectory = File( settings.getString("eu_lastImageDir", System.getProperty("user.home")) )
    }

    val result = chooser.showOpenDialog(null)
    if (result != JFileChooser.APPROVE_OPTION) {
        throw IllegalStateException("ファイルが選択されませんでした")
    }

    val file = chooser.selectedFile ?: throw IllegalStateException("ファイルが選択されませんでした")

    // ImageIO で読み込む
    val buffered = ImageIO.read(file) ?: throw IllegalArgumentException("画像を読み込めませんでした: ${file.absolutePath}")

    // 最後に開いたディレクトリを保存
    settings.putString("eu_lastImageDir", file.parentFile?.absolutePath ?: System.getProperty("user.home"))

    // Skiko の拡張を使って ImageBitmap に変換
    return buffered.toComposeImageBitmap()
}