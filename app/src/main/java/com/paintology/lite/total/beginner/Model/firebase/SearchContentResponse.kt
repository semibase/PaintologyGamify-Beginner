package com.paintology.lite.total.beginner.Model.firebase

import com.paintology.lite.total.beginner.Model.CommunityPost


class SearchContentResponse (
    var community_posts: List<CommunityPost> = listOf(),
    var tutorials: List<Post> = listOf(),
    var posts: List<Post> = listOf()
)
