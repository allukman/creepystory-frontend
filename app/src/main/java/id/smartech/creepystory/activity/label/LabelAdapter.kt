package id.smartech.creepystory.activity.label

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.smartech.creepystory.R
import id.smartech.creepystory.databinding.ItemLabelBinding
import id.smartech.creepystory.model.label.LabelModel

class LabelAdapter(private val items: ArrayList<LabelModel>): RecyclerView.Adapter<LabelAdapter.labelHolder>() {

    class labelHolder(val binding: ItemLabelBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<LabelModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): labelHolder {
        return labelHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_label,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: labelHolder, position: Int) {
        val item = items[position]

        holder.binding.label.text = item.laName
    }
}