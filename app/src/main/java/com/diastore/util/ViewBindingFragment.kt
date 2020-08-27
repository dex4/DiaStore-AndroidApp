package com.diastore.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    val binding: VB
        get() = _binding
            ?: throw IllegalStateException("Tried to call an auto-cleared value outside of the view lifecycle.")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        binding.root

    fun setViewBinding(viewBinding: VB) {
        _binding = viewBinding
    }
}