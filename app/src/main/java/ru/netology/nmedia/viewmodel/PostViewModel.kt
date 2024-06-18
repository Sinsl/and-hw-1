package ru.netology.nmedia.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() {
        repository.setLike()
    }
    fun share() {
        repository.setShare()
    }
    fun view() {
        repository.setView()
    }
}