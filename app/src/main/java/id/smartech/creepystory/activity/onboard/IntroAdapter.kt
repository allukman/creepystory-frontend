package id.smartech.creepystory.activity.onboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.smartech.creepystory.R

class IntroAdapter(private val introModels: List<IntroModel>): RecyclerView.Adapter<IntroAdapter.IntroSlideViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_onboard,
            parent, false
        ))
    }

    override fun getItemCount(): Int = introModels.size

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introModels[position])
    }

    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTitle = view.findViewById<TextView>(R.id.tv_title)
        private val textDesc = view.findViewById<TextView>(R.id.tv_desc)
        private val imageIcon = view.findViewById<ImageView>(R.id.iv_image)

        fun bind(introModel: IntroModel) {
            textTitle.text = introModel.title
            textDesc.text = introModel.description
            imageIcon.setImageResource(introModel.image)
        }
    }
}