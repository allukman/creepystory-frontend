package id.smartech.creepystory.activity.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.creepystory.R
import id.smartech.creepystory.databinding.ItemHomeFavoritedBinding
import id.smartech.creepystory.model.story.StoryModel
import id.smartech.creepystory.remote.ApiClient.Companion.BASE_URL_IMAGE

class HomeFavoritedAdapter(private val items: ArrayList<StoryModel>): RecyclerView.Adapter<HomeFavoritedAdapter.HomeFavoritedHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<StoryModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class HomeFavoritedHolder(val binding: ItemHomeFavoritedBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFavoritedHolder {
        return HomeFavoritedHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_home_favorited,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeFavoritedHolder, position: Int) {
        val item = items[position]
        val imgStory = BASE_URL_IMAGE + item.stPhotoCover
        val imgProfile = BASE_URL_IMAGE + item.mePhotoProfile
        val category = "(" + item.ctName + ")"

        holder.binding.tvTitle.text = item.stTitle
        holder.binding.tvContent.text = item.stContent
        holder.binding.tvCategory.text = category
        holder.binding.tvName.text = item.meName
        holder.binding.tvFavorited.text = item.stFavorited
        holder.binding.tvCreated.text = item.stCreated.split("T")[0]

        Glide.with(holder.itemView)
                .load(imgStory)
                .placeholder(R.drawable.book)
                .error(R.drawable.book)
                .into(holder.binding.ivImage)

        Glide.with(holder.itemView)
                .load(imgProfile)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(holder.binding.profilePhoto)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(storyModel: StoryModel)
    }
}