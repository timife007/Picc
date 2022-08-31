package com.timife.pix.presentation.pix_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.timife.pix.R
import com.timife.pix.databinding.FragmentPixListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PixListFragment : Fragment() {
    private lateinit var listBinding : FragmentPixListBinding

    private val listViewModel by viewModels<PixListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listBinding = FragmentPixListBinding.inflate(inflater)
        return listBinding.root
    }
}