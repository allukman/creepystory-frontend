package id.smartech.creepystory.activity.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.smartech.creepystory.R
import id.smartech.creepystory.databinding.ItemFavoritedBinding
import id.smartech.creepystory.model.favorite.FavoriteModel
import id.smartech.creepystory.remote.ApiClient

class FavoriteAdapter(private val items: ArrayList<FavoriteModel>): RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun addList(list: List<FavoriteModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class FavoriteHolder(val binding: ItemFavoritedBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            DataBindingUtil.inflate(
                (LayoutInflater.from(parent.context)),
                R.layout.item_favorited,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val item = items[position]
        val imgStory = ApiClient.BASE_URL_IMAGE + item.stPhotoCover
        val imgProfile = ApiClient.BASE_URL_IMAGE + item.mePhotoProfile

        holder.binding.tvTitle.text = item.stTitle
        holder.binding.tvContent.text = item.stContent
        holder.binding.tvName.text = item.meName
        holder.binding.tvFavorited.text = item.stFavorited
        holder.binding.favoriteDate.text = item.faCreated.split("T")[0]

        Glide.with(holder.itemView)
            .load(imgStory)
            .placeholder(R.drawable.book)
            .error(R.drawable.book)
            .into(holder.binding.ivImage)

        Glide.with(holder.itemView)
            .load(imgProfile)
            .placeholder(R.drawable.book)
            .error(R.drawable.book)
            .into(holder.binding.profilePhoto)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onClickItem(item)
        }
    }

    interface OnItemClickCallback {
        fun onClickItem(data: FavoriteModel)
    }

}