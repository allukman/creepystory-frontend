package id.smartech.creepystory.activity.story.liststory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.creepystory.R
import id.smartech.creepystory.databinding.ItemStoryBinding
import id.smartech.creepystory.model.story.StoryModel
import id.smartech.creepystory.remote.ApiClient

class ListStoryAdapter(private val items: ArrayList<StoryModel>): RecyclerView.Adapter<ListStoryAdapter.ListStoryHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ListStoryAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<StoryModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class ListStoryHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryHolder {
        return ListStoryHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_story,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListStoryHolder, position: Int) {
        val item = items[position]
        val imgStory = ApiClient.BASE_URL_IMAGE + item.stPhotoCover
        val category = "(" + item.ctName + ")"

        holder.binding.tvTitle.text = item.stTitle
        holder.binding.tvContent.text = item.stContent
        holder.binding.tvCategory.text = category
        holder.binding.tvCreated.text = item.stCreated.split("T")[0]
        holder.binding.tvFavorited.text = item.stFavorited

        Glide.with(holder.itemView)
            .load(imgStory)
            .placeholder(R.drawable.white)
            .error(R.drawable.white)
            .into(holder.binding.ivImage)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(storyModel: StoryModel)
    }

}