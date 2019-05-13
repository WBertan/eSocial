package com.bertan.esocial.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bertan.esocial.R
import com.bertan.esocial.extension.asBoolean
import com.bertan.esocial.extension.loadUrl
import com.bertan.esocial.extension.toColorStateList
import com.bertan.presentation.model.SourceView
import kotlinx.android.synthetic.main.adapter_source_item.view.*

class SourceViewAdapter(private val sources: List<SourceView>) : RecyclerView.Adapter<SourceViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(source: SourceView) {
            itemView.logo.loadUrl(source.icon)

            itemView.button.apply {
                text = source.name
                backgroundTintList = source.colour.toColorStateList
                isEnabled = source.state.asBoolean
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sources[position])

    override fun getItemCount() = sources.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_source_item, parent, false))
}