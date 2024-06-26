package io.github.caimucheng.leaf.ide.fragment

import androidx.lifecycle.lifecycleScope
import io.github.caimucheng.leaf.ide.R
import io.github.caimucheng.leaf.ide.util.findGlobalNavController
import io.github.caimucheng.leaf.ide.viewmodel.AppIntent
import io.github.caimucheng.leaf.ide.viewmodel.AppViewModel
import io.github.caimucheng.leaf.module.creator.FragmentCreator
import io.github.caimucheng.leaf.module.fragment.ModuleFragment
import kotlinx.coroutines.launch

class NewProjectFragment : BaseModuleFragment() {

    override fun getModuleFragment(fragmentCreator: FragmentCreator): ModuleFragment {
        return fragmentCreator.onNewProject()
    }

    override fun onPopBackHome(refreshModule: Boolean) {
        if (refreshModule) {
            viewLifecycleOwner.lifecycleScope.launch {
                AppViewModel.intent.send(AppIntent.Refresh)
            }
        }
        findGlobalNavController().navigate(
            R.id.action_newProjectFragment_to_mainFragment
        )
    }

}