package com.bertan.esocial.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bertan.esocial.R
import com.bertan.presentation.model.SourceView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_source_item.view.*

class SourceViewAdapter(private val sources: List<SourceView>) : RecyclerView.Adapter<SourceViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(source: SourceView) {
            Picasso //TODO(Move to DI)
                    .get()
                    .load(source.icon)
                    .into(itemView.logo)

            itemView.button.apply {
                text = source.name
                val color: Int =
                        when (val sourceColour = source.colour) {
                            is SourceView.ColourView.RGB -> {
                                val (red, green, blue) = sourceColour
                                Color.rgb(red, green, blue)
                            }
                            is SourceView.ColourView.Hex ->
                                Color.parseColor(sourceColour.value)
                        }
                backgroundTintList = ColorStateList.valueOf(color)
            }

            when (source.state) {
                is SourceView.StateView.Enabled ->
                    Unit
                is SourceView.StateView.Disabled -> {
                    itemView.button.isEnabled = false
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(sources[position])

    override fun getItemCount() = sources.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_source_item, parent, false))
}