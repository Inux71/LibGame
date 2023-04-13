package com.grabieckacper.libgame.model

import com.grabieckacper.libgame.common.enums.Status

data class Game(
    val id: Long? = null,
    val title: String? = null,
    val thumbnail: String? = null,
    val genre: String? = null,
    var status: Status? = null
)
