package com.bertan.data.remote.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.remote.model.PostModel
import com.bertan.data.remote.randomDate

object PostMapper {
    val PostModel.asPostEntity: PostEntity
        get() =
            PostEntity(
                id.toString(),
                title,
                BodyEntity(
                    BodyEntity.TypeEntity.Text,
                    body
                ),
                "https://jsonplaceholder.typicode.com/posts/$id",
                randomDate()
            )
}