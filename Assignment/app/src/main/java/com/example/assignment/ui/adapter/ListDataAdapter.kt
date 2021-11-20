package com.example.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.assignment.BR
import com.example.assignment.R
import com.example.assignment.databinding.ItemListBinding
import com.example.assignment.model.ListData


class ListDataAdapter(
    private var sampleList: List<ListData?>?
) :
    RecyclerView.Adapter<ListDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemListBinding = ItemListBinding.inflate(inflater, parent, false)
        return ViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (sampleList?.get(position)?.title.isNullOrEmpty()) {
            sampleList?.get(position)?.title =
                holder.itemListBinding.txtTitle.context.getString(R.string.dummy_title)
        }

        if (sampleList?.get(position)?.description.isNullOrEmpty()) {
            sampleList?.get(position)?.description =
                holder.itemListBinding.txtSubTitle.context.getString(R.string.dummy_description)
        }

        Glide.with(holder.itemListBinding.imgTitle).load(sampleList?.get(position)?.imgHref)
            .apply(RequestOptions().placeholder(R.drawable.ic_img_placeholder))
            .into(holder.itemListBinding.imgTitle)

        holder.bind(sampleList?.get(position))
    }

    override fun getItemCount(): Int {
        return sampleList?.size ?: 0
    }

    fun setListData(it: List<ListData?>?) {
        if (!it.isNullOrEmpty())
            this.sampleList = it
    }

    inner class ViewHolder(val itemListBinding: ItemListBinding) :
        RecyclerView.ViewHolder(itemListBinding.root) {
        fun bind(listData: ListData?) {
            itemListBinding.setVariable(BR.listData, listData)
            itemListBinding.executePendingBindings()
        }
    }
}