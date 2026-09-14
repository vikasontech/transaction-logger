package org.radhe.transaction_logger.sessions.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.radhe.transaction_logger.sessions.domain.vo.SessionId
import org.radhe.transaction_logger.sessions.domain.vo.SessionVo
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

@Entity
@Table(name = "session")
data class SessionEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
//    @Column(name="account_id")
    val accountId: String,
    @Column(name = "session_year")
    val year: Int,
    @Column(name = "session_month")
    val month: Int,
    @Column(name="created_time")
    val createdTime: LocalDateTime,
    @Column(name="expired_time")
    val expiredTime: LocalDateTime?=null
) {

    fun toDomain(): SessionVo {
        return SessionVo(
            sessionId = if(id == null) null else SessionId(id!!),
            accountId = AccountId(value = accountId),
            year = year, month = month, createdTime = createdTime, expiredTime = expiredTime
        )
    }
}