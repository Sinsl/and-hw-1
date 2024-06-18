package ru.netology.nmedia.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) {post ->
            setData(post, binding)

        }
        addListeners(binding, viewModel)
    }

    private fun setData(post: Post, binding: ActivityMainBinding){
        with(binding){
            author.text = post.author
            published.text = post.published
            postContent.text = post.content
            like.setImageResource(
                if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )
            likeCount.text = countToString(post.likeCount)
            shareCount.text = countToString(post.shareCount)
            viewCount.text = countToString(post.viewCount)
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
    private fun addListeners(binding: ActivityMainBinding, viewModel: PostViewModel ){
        with(binding) {

            like.setOnClickListener {
                viewModel.like()
            }
            share.setOnClickListener {
                viewModel.share()
            }
            icEye.setOnClickListener {
                viewModel.view()
            }
        }
    }
}