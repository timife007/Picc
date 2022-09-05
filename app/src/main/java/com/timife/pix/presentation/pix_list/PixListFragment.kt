package com.timife.pix.presentation.pix_list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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
        searchFunction()
        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PixListAdapter(requireContext(),PixListAdapter.OnPixClickListener{
            listViewModel.displayImages(it)
        })


        listViewModel.pixList.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
            adapter.notifyDataSetChanged()
        })
        bindListData(adapter)
        navigateToDetail()
    }


    private fun bindListData(adapter:PixListAdapter){

        //Attach adapter
        listBinding.apply {
            pixRecyclerView.setHasFixedSize(true)
            listBinding.pixRecyclerView.adapter = adapter
            pixRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PixLoadStateAdapter { adapter.retry() },
                footer = PixLoadStateAdapter { adapter.retry() }
            )
            recyclerRetry.setOnClickListener {
                adapter.retry()
            }
        }
        //Handle paging loading state
        adapter.addLoadStateListener { loadState ->
            listBinding.apply {
                listProgress.isVisible = loadState.refresh is LoadState.Loading
//                pixRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
                recyclerRetry.isVisible =
                    loadState.refresh is LoadState.Error
                errorMessage.isVisible = loadState.refresh is LoadState.Error
                queryNoResultText.isVisible =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            }
        }


    }

    //Handle Navigation to detail screen
    private fun navigateToDetail(){
        listViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer {pixItem->
            if(pixItem != null){
                this.findNavController()
                    .navigate(
                        PixListFragmentDirections.actionPixListFragmentToPixDetailFragment(
                            pixItem
                        )
                    )
                listViewModel.displayImagesComplete()
            }
        })
    }

    private fun searchFunction(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object:MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.pix_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return when(menuItem.itemId){
                    R.id.search_menu ->{
                        val searchView = menuItem.actionView as SearchView

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
                        true
                    }
                    else -> false
                }
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }
}