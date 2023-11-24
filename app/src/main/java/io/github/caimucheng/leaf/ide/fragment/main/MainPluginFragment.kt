package io.github.caimucheng.leaf.ide.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.caimucheng.leaf.ide.adapter.MainPluginAdapter
import io.github.caimucheng.leaf.ide.databinding.FragmentMainPluginBinding
import io.github.caimucheng.leaf.ide.model.Plugin
import io.github.caimucheng.leaf.ide.viewmodel.AppIntent
import io.github.caimucheng.leaf.ide.viewmodel.AppViewModel
import io.github.caimucheng.leaf.ide.viewmodel.MainViewModel
import io.github.caimucheng.leaf.ide.viewmodel.PluginState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainPluginFragment : Fragment() {

    private lateinit var viewBinding: FragmentMainPluginBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val plugins by lazy {
        ArrayList<Plugin>(AppViewModel.state.value.plugins)
    }

    private val adapter by lazy {
        MainPluginAdapter(
            requireContext(),
            plugins
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainPluginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            AppViewModel.state.collectLatest {
                when (it.pluginState) {
                    PluginState.Loading -> {
                        viewBinding.content.visibility = View.GONE
                        viewBinding.placeholder.visibility = View.GONE
                        viewBinding.loading.visibility = View.VISIBLE
                    }

                    PluginState.Done -> {
                        viewBinding.loading.visibility = View.GONE
                        if (plugins.isNotEmpty()) {
                            val itemCount = plugins.size
                            plugins.clear()
                            adapter.notifyItemRangeRemoved(0, itemCount)
                        }
                        if (it.plugins.isNotEmpty()) {
                            plugins.addAll(it.plugins)
                            adapter.notifyItemRangeInserted(0, it.plugins.size)
                        }
                        if (plugins.isNotEmpty()) {
                            viewBinding.placeholder.visibility = View.GONE
                            viewBinding.content.visibility = View.VISIBLE
                        } else {
                            viewBinding.content.visibility = View.GONE
                            viewBinding.placeholder.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        viewBinding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewBinding.recyclerView.adapter = adapter
    }

}