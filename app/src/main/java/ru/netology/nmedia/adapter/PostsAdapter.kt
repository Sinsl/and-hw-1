package ru.netology.nmedia.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode


interface OnInteractionListener {
    fun like(post: Post)
    fun share(post: Post)
    fun view(post: Post)
    fun  remove(post: Post)
    fun edit(post: Post)
    fun play(post: Post)
}

class PostsAdapter(private val onInteractionListener: OnInteractionListener):  ListAdapter<Post, PostViewHolder>(PostDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            postContent.text = post.content
            likeBtn.isChecked = post.likedByMe
            likeBtn.text = countToString(post.likeCount)
//            like.setImageResource(
//                if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
//            )
//            likeCount.text = countToString(post.likeCount)
            shareBtn.text = countToString(post.shareCount)
            viewCount.text = countToString(post.viewCount)
            if (post.videoUrl.isNotBlank()) {
                videoGroup.visibility = View.VISIBLE
            }

            likeBtn.setOnClickListener {
                onInteractionListener.like(post)
            }
            shareBtn.setOnClickListener {
                onInteractionListener.share(post)
            }
            icEye.setOnClickListener {
                onInteractionListener.view(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.edit -> {
                                onInteractionListener.edit(post)
                                true
                            }
                            R.id.remove -> {
                                onInteractionListener.remove(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            videoImg.setOnClickListener {
                Log.i("ma", "start adapter video")
                onInteractionListener.play(post)
            }
        }
    }


    private  fun countToString(num: Long): String {
        val res = when(num) {
            in 0..999 -> num.toString()
            in 1000..1099 -> "1K"
            in 1100..9999 -> (num.toDouble() / 1000.0).toBigDecimal().setScale(1, RoundingMode.DOWN).toString() + "K"
            in 10000..999999 -> (num.toDouble() / 1000.0).toInt().toString() + "K"
            in 1000000..1099999 -> (num.toDouble() / 1000000.0).toInt().toString() + "M"
            else -> (num.toDouble() / 1000000.0).toBigDecimal().setScale(1, RoundingMode.DOWN).toString() + "M"
        }
        return res
    }
}

object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}