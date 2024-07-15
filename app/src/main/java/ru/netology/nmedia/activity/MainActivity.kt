package ru.netology.nmedia.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object: OnInteractionListener {
            override fun like(post: Post) {
                viewModel.like(post.id)
            }

            override fun share(post: Post) {
                viewModel.share(post.id)
            }

            override fun view(post: Post) {
                viewModel.view(post.id)
            }

            override fun remove(post: Post) {
                viewModel.remove(post.id)
            }

            override fun edit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }


        viewModel.edited.observe(this) {
            if (it.id != 0L) {
                binding.content.setText(it.content)
                binding.content.focusAndShowKeyboard()
                binding.group.visibility = View.VISIBLE
                binding.editMessage.setText(it.content)
            }
        }
        binding.save.setOnClickListener {
            val content = binding.content.text.toString()
            if (content.isBlank()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContentAndSave(content)

            binding.content.setText("")
            binding.editMessage.setText("")
            binding.group.visibility = View.GONE
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content)
        }
        binding.editClose.setOnClickListener {
            viewModel.clear()
            binding.content.setText("")
            binding.editMessage.setText("")
            binding.group.visibility = View.GONE
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content)
        }
    }
}