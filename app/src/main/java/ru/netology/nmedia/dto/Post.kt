package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likeCount: Long = 10,
    val shareCount: Long = 0,
    val viewCount: Long = 0
)