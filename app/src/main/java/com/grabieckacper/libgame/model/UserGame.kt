package com.grabieckacper.libgame.model

import com.grabieckacper.libgame.common.enums.Status

data class UserGame(val gameId: Long? = null, val status: Status? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is UserGame) {
            return false
        }

        return this.gameId == other.gameId
    }

    override fun hashCode(): Int {
        var result = gameId?.hashCode() ?: 0
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }
}
