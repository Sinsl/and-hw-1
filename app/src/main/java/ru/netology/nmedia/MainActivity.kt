package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий.",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интесивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но, самое важное остается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен",
            published = "21 мая в 18:36",
            likeCount = 9999,
            shareCount = 999999,
            viewCount = 1099999
        )
        setData(post, binding)
        addListeners(post, binding)
    }

    private fun setData(post: Post, binding: ActivityMainBinding){
        with(binding){
            author.text = post.author
            published.text = post.published
            postContent.text = post.content
            renderPost(post, binding)
        }
    }

    private fun renderPost(post: Post, binding: ActivityMainBinding) {
        with(binding) {
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
    private fun addListeners(post: Post, binding: ActivityMainBinding){
        with(binding) {
            root.setOnClickListener {
                Log.i("pi", "click root")
            }

            like.setOnClickListener {
                post.likeCount += if (post.likedByMe) -1 else 1
                post.likedByMe = !post.likedByMe
                renderPost(post, binding)
                Log.i("pi", "click like")
            }
            share.setOnClickListener {
                post.shareCount++
                renderPost(post, binding)
            }
            icEye.setOnClickListener {
                post.viewCount++
                renderPost(post, binding)
            }
        }
    }
}