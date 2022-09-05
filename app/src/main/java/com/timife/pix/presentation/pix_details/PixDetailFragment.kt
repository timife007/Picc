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
import com.timife.pix.presentation.adapters.bindImage
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pix = PixDetailFragmentArgs.fromBundle(requireArguments()).selectedPix

        detailBinding.apply {
            bindImage(detailImage,pix.largeImageUrl)
            commentText.text = pix.comments.toString()
            downloadsText.text = pix.downloads.toString()
            likesText.text = pix.likes.toString()
            tags.text = pix.tags
            userName.text = pix.userName
        }
    }
}