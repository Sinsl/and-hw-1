package ru.netology.nmedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode

interface OnInteractionListener {
    fun like(post: Post)
    fun share(post: Post)
    fun view(post: Post)
}

class PostsAdapter(private val onInteractionListener: OnInteractionListener):  RecyclerView.Adapter<PostViewHolder>() {

    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.i("pi", "onCreateViewHolder")
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        Log.i("pi", post.published)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        Log.i("pi", "PostViewHolder bind")
        binding.apply {
            author.text = post.author
            published.text = post.published
            postContent.text = post.content
            like.setImageResource(
                if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )
            likeCount.text = countToString(post.likeCount)
            shareCount.text = countToString(post.shareCount)
            viewCount.text = countToString(post.viewCount)

            like.setOnClickListener {
                onInteractionListener.like(post)
            }
            share.setOnClickListener {
                onInteractionListener.share(post)
            }
            icEye.setOnClickListener {
                onInteractionListener.view(post)
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