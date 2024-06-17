package ru.netology.nmedia

class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likeCount: Long = 0,
    var shareCount: Long = 0,
    var viewCount: Long = 0
)