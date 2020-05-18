package com.diastore.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.diastore.BR

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel>(@LayoutRes private val layoutRes: Int) : Fragment() {

    abstract val viewModel: V
    private var _binding: B? = null
    val binding: B
        get() = _binding
            ?: throw IllegalStateException("Tried to call an auto-cleared value outside of the view lifecycle.")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        DataBindingUtil.inflate<B>(inflater, layoutRes, container, false).also {
            _binding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }
}