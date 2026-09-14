package org.radhe.transaction_logger.sessions.domain.vo

import org.radhe.transaction_logger.sessions.infrastructure.persistence.SessionEntity
import java.time.LocalDateTime

data class SessionVo(
    val sessionId: SessionId?=null,
    val accountId: AccountId,
    val year: Int,
    val month: Int,
    val createdTime: java.time.LocalDateTime,
    val expiredTime: java.time.LocalDateTime?=null
) {
    fun closeSession() {
        TODO("Not yet implemented")
    }

}
