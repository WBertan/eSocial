package com.bertan.data.mapper

import com.bertan.data.model.PostEntity
import com.bertan.domain.model.Post

object PostEntityMapper {
    val PostEntity?.asPost: Post?
        get() = mapTo {
            Post(
                it.accountId,
                it.id,
                it.title,
                it.bodyId,
                it.url,
                it.createdDate
            )
        }
}

object PostMapper {
    val Post?.asPostEntity: PostEntity?
        get() = mapTo {
            PostEntity(
                it.accountId,
                it.id,
                it.title,
                it.bodyId,
                it.url,
                it.createdDate
            )
        }
}