package com.timife.pix.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.pix.databinding.PixListItemBinding
import com.timife.pix.domain.model.Pix

class PixListAdapter(private val clickListener: OnPixClickListener) :
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
            if (pixItem != null) {
                clickListener.onClick(pixItem)
            }
        }
//        holder.itemView.dropdown.setOnClickListener {
//            val inventoryItemOptions = arrayOf("Delete")
//            MaterialAlertDialogBuilder(context).setTitle("")
//                .setItems(inventoryItemOptions) { dialog,
//                                                  which ->
//                    MaterialAlertDialogBuilder(context).setTitle("Delete Item").setMessage("Do you want to completely delete this classification?").setNegativeButton("No"){
//                            dialog, _ ->
//                        dialog.dismiss()
//                    }.setPositiveButton("Yes"){
//                            dialog, _ ->
//                        onDeleteListener.delete(inventoryProduct!!)
//                        notifyDataSetChanged()
//                        dialog.dismiss()
//                    }.show()
//                }.show()
//        }
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