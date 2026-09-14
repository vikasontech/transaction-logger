package org.radhe.transaction_logger.sessions.domain.model

import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.radhe.transaction_logger.sessions.domain.vo.CreateSessionRequest
import org.radhe.transaction_logger.sessions.domain.vo.SessionPeriod
import org.radhe.transaction_logger.sessions.domain.vo.SessionVo
import org.radhe.transaction_logger.sessions.infrastructure.persistence.SessionEntity
import java.time.LocalDateTime

@ConsistentCopyVisibility
data class Session private constructor(
    val id : Long?=null,
    val sessionPeriod: SessionPeriod,
    val accountId: AccountId,
    val createTime: LocalDateTime,
    val expiredTime: LocalDateTime?=null,
) {

    companion object {
        fun open(createSessionRequest: CreateSessionRequest): Session {
            return Session(
                sessionPeriod = createSessionRequest.sessionPeriod,
                accountId = createSessionRequest.accountId,
                createTime = LocalDateTime.now()
            )
        }

        fun createSession(sessionEntity: SessionVo): Session {
             return Session(
                 sessionPeriod = SessionPeriod(sessionEntity.year, sessionEntity.month),
                 accountId = sessionEntity.accountId,
                 createTime = sessionEntity.createdTime,
                 expiredTime = sessionEntity.expiredTime,
                 id = sessionEntity.sessionId?.value
             )
        }
    }


    fun entity(): SessionEntity{
        return SessionEntity(
            id = id,
            year = sessionPeriod.year,
            month = sessionPeriod.month,
            accountId = accountId.value,
            createdTime = createTime,
            expiredTime = expiredTime )
    }

    fun close(): Session {
        if(this.expiredTime != null) {
            throw IllegalStateException("Session already expired!")
        }
        return this.copy(expiredTime = LocalDateTime.now())
    }

}
