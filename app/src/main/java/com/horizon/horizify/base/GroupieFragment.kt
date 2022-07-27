package com.horizon.horizify.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.horizon.horizify.R
import com.horizon.horizify.databinding.FragmentBaseBinding
import com.xwray.groupie.Section
import org.koin.core.component.KoinComponent

abstract class GroupieFragment constructor(@LayoutRes val baseLayout: Int = R.layout.fragment_base) : Fragment(baseLayout), KoinComponent {

    private lateinit var binding: FragmentBaseBinding

    protected val root by lazy { Section() }

    private val rootAdapter by lazy { RootAdapter() attach root }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            with(rvRoot) {
                rvRoot.adapter = rootAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        onViewSetup(view, savedInstanceState)
    }

    abstract fun onViewSetup(view: View, savedInstanceState: Bundle?)

    /**
     * Use to navigate to pages
     * Supply Page ID of fragment class
     */
    protected fun navigateToPage(destination: String) {
        val action = createGlobalAction(destination)
        navigate(action)
    }

    private fun navigate(@IdRes destination: Int) {
        view?.findNavController()?.navigate(destination)
    }

    /**
     * Use to convert Page ID string to layout Id Int
     * Todo : Add exception when page supplied is not found
     */
    private fun createGlobalAction(customAction: String): Int {
        val act = requireActivity()
        return act.resources.getIdentifier(customAction, DEF_TYPE, act.packageName)
    }

    /**
     * Use for on back pressed
     * Could have a custom destination
     */
    protected fun popBack() {
        view?.findNavController()?.popBackStack()
    }

    companion object {
        private const val DEF_TYPE = "id"
    }
}