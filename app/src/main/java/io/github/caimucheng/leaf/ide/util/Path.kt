package io.github.caimucheng.leaf.ide.util

import android.os.Environment
import io.github.caimucheng.leaf.ide.application.AppContext
import io.github.caimucheng.leaf.ide.viewmodel.LaunchMode
import java.io.File

val ExternalRootPath: File = Environment.getExternalStorageDirectory()

inline val LeafIDEPluginRootPath: File
    get() {
        return File(AppContext.current.filesDir, "plugins")
    }

inline val LeafIDERootPath: File
    get() {
        return when (AppContext.current.launchMode) {
            LaunchMode.LaunchFromExteralStorage -> File(ExternalRootPath, "LeafIDE")
            LaunchMode.LaunchFromInternalStorage -> File(AppContext.current.filesDir, "LeafIDE")
        }
    }

inline val LeafIDECrashFilePath: File
    get() {
        return File(AppContext.current.filesDir, "crash")
    }

val LeafIDEProjectPath: File by lazy { File(LeafIDERootPath, "projects") }

