package com.grabieckacper.libgame.model

import com.grabieckacper.libgame.common.enums.Status

data class UserGame(
    val gameId: Long? = null,
    val status: Status? = null
)
