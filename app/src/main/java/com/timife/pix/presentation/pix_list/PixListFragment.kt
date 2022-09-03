package com.timife.pix.presentation.pix_list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.pix.R
import com.timife.pix.databinding.FragmentPixListBinding
import com.timife.pix.presentation.adapters.PixListAdapter
import com.timife.pix.presentation.adapters.PixLoadStateAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PixListAdapter(PixListAdapter.OnPixClickListener{
            listViewModel.displayImages(it)
        })
        bindListData(adapter)
        navigateToDetail()
    }

    private fun bindListData(adapter:PixListAdapter){
        listBinding.apply {
            pixRecyclerView.setHasFixedSize(true)
            pixRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PixLoadStateAdapter { adapter.retry() },
                footer = PixLoadStateAdapter { adapter.retry() }
            )
            recyclerRetry.setOnClickListener {
                adapter.retry()
            }
        }
        adapter.addLoadStateListener { loadState ->
            listBinding.apply {
                listProgress.isVisible = loadState.refresh is LoadState.Loading
                pixRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
                recyclerRetry.isVisible =
                    loadState.refresh is LoadState.Error && adapter.itemCount == 0
                errorMessage.isVisible = loadState.refresh is LoadState.Error
                queryNoResultText.isVisible =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            }
        }

        listViewModel.pixList.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun navigateToDetail(){
        listViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer {pixItem->
            MaterialAlertDialogBuilder(requireContext(),R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle(requireContext().getString(R.string.see_more_details))
                .setMessage("Get more details about ${pixItem?.userName}")
                .setNegativeButton(requireContext().getString(R.string.cancel)){
                        dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton(requireContext().getString(R.string.okay)){
                        dialog, _ ->run {
                    this.findNavController()
                        .navigate(
                            PixListFragmentDirections.actionPixListFragmentToPixDetailFragment(
                                pixItem
                            )
                        )
                    listViewModel.displayImagesComplete()
                    dialog.dismiss()
                }
                }.show()
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    listBinding.pixRecyclerView.scrollToPosition(0)
                    listViewModel.getSearchPix(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }





}