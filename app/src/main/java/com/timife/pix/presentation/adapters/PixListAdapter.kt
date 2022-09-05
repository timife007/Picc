package com.timife.pix.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.pix.R
import com.timife.pix.databinding.PixListItemBinding
import com.timife.pix.domain.model.Pix
import com.timife.pix.presentation.pix_list.PixListFragmentDirections

class PixListAdapter(private val context: Context, private val clickListener: OnPixClickListener) :
    PagingDataAdapter<Pix, PixListAdapter.PixViewHolder>(PixDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixViewHolder {
        return PixViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: PixViewHolder, position: Int) {
        val pixItem = getItem(position)

        if (pixItem != null) {
            holder.bind(pixItem)
        }
        holder.itemView.setOnClickListener {
                MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
                    .setTitle(context.getString(R.string.see_more_details))
                    .setMessage("Get more details about ${pixItem?.userName}")
                    .setNegativeButton(context.getString(R.string.cancel)){
                            dialog, _ ->
                        dialog.dismiss()
                    }.setPositiveButton(context.getString(R.string.okay)){
                            dialog, _ ->
                            clickListener.onClick(pixItem!!)
                            dialog.dismiss()

                    }.show()
//            if (pixItem !=null){
//                clickListener.onClick(pixItem)
//
//            }
        }

    }

    class PixViewHolder(private val binding: PixListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pix) {
            binding.pixItem = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PixViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PixListItemBinding.inflate(layoutInflater, parent, false)
                return PixViewHolder(binding)

            }
        }
    }

    class PixDiffUtil : DiffUtil.ItemCallback<Pix>() {
        override fun areItemsTheSame(oldItem: Pix, newItem: Pix): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pix, newItem: Pix): Boolean {
            return oldItem.userName == newItem.userName
        }
    }

    class OnPixClickListener(val clickListener:(item: Pix) -> Unit){
        fun onClick(item: Pix){
            clickListener(item)
        }
    }
}