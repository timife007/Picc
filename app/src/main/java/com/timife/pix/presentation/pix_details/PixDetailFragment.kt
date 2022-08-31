package com.timife.pix.presentation.pix_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.timife.pix.R
import com.timife.pix.databinding.FragmentPixDetailBinding
import com.timife.pix.databinding.FragmentPixListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PixDetailFragment : Fragment() {
    private lateinit var detailBinding: FragmentPixDetailBinding
    private val detailViewModel by viewModels<PixDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentPixDetailBinding.inflate(inflater)
        return detailBinding.root
    }
}