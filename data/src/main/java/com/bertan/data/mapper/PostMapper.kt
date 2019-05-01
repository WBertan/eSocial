package com.bertan.data.mapper

import com.bertan.data.model.PostEntity
import com.bertan.domain.model.Post

object PostEntityMapper {
    val PostEntity.asPost: Post
        get() =
            Post(
                accountId,
                id,
                title,
                bodyId,
                url,
                createdDate
            )
}

object PostMapper {
    val Post.asPostEntity: PostEntity
        get() =
            PostEntity(
                accountId,
                id,
                title,
                bodyId,
                url,
                createdDate
            )
}