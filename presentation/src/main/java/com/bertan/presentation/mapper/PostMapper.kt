package com.bertan.presentation.mapper

import com.bertan.domain.model.Post
import com.bertan.presentation.mapper.BodyMapper.asBodyView
import com.bertan.presentation.mapper.BodyViewMapper.asBody
import com.bertan.presentation.model.PostView

object PostViewMapper {
    val PostView.asPost: Post
        get() =
            Post(
                id,
                title,
                body?.asBody,
                url,
                createdDate
            )
}

object PostMapper {
    val Post.asPostView: PostView
        get() =
            PostView(
                id,
                title,
                body?.asBodyView,
                url,
                createdDate
            )
}