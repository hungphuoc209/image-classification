package com.example.cameraxtest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxtest.LabelAdapter.LabelVH
import com.example.cameraxtest.databinding.LabelItemBinding
import com.google.mlkit.vision.label.ImageLabel

class LabelAdapter(
    private val context: Context,
    private val labels: List<ImageLabel>
) : RecyclerView.Adapter<LabelVH>() {

    private var dict: Array<String> = arrayOf("Jicama", "Onion", "Nothing")


    inner class LabelVH(private val binding: LabelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindView(position: Int) {
            binding.tvIndex.text = labels[position].index.toString()
            binding.tvLabel.text = dict[labels[position].index]
            binding.tvPercent.text = String.format(
                context.getString(R.string.percent),
                (labels[position].confidence) * 100
            ) + "%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelVH {
        val binding = LabelItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return LabelVH(binding)
    }

    override fun onBindViewHolder(holder: LabelVH, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = labels.size
}