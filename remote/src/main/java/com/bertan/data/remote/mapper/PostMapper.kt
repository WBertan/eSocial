package com.bertan.data.remote.mapper

import com.bertan.data.model.BodyEntity
import com.bertan.data.model.PostEntity
import com.bertan.data.remote.model.PostModel
import com.bertan.data.remote.randomDate

object PostMapper {
    val PostModel.asPostEntity: Pair<PostEntity, BodyEntity>
        get() =
            Pair(
                PostEntity(
                    id.toString(),
                    title,
                    "post_$id",
                    "https://jsonplaceholder.typicode.com/posts/$id",
                    randomDate()
                ),
                BodyEntity(
                    "post_$id",
                    BodyEntity.TypeEntity.Text,
                    body
                )
            )
}