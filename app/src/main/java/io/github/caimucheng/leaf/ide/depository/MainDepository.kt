package io.github.caimucheng.leaf.ide.depository

import io.github.caimucheng.leaf.ide.util.LeafIDEProjectPath
import io.github.caimucheng.leaf.ide.util.LeafIDERootPath
import io.github.caimucheng.leaf.ide.viewmodel.AppIntent
import io.github.caimucheng.leaf.ide.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MainDepository {

    fun initialize() {
        createLeafIDERoot()
        createLeafIDEProject()
    }

    private fun createLeafIDERoot() {
        LeafIDEProjectPath.mkdirs()
    }

    private fun createLeafIDEProject() {
        LeafIDEProjectPath.mkdirs()

        runCatching {
            File(LeafIDERootPath, ".nomedia")
                .createNewFile()
        }
    }

}