package org.radhe.transaction_logger.sessions.domain.repository

import org.hibernate.Session
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.radhe.transaction_logger.sessions.domain.vo.SessionId
import org.radhe.transaction_logger.sessions.infrastructure.persistence.SessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Month

interface SessionRepository: JpaRepository<SessionEntity, Long> {
    @Query(value="select * from session where account_id = :accountId " +
            "and session_month = :month and session_year = :year and expired_time is null",
        nativeQuery = true)
    fun findByAccountIdAndExpiryTime(accountId: String, year: Int, month: Int): List<SessionEntity>

    @Query(value="select * from session where id = :sessionId and expired_time is null",
        nativeQuery = true)
    fun findBySessionIdAndExpiryTime(sessionId: Long): List<SessionEntity>

    @Query(value="select * from session where expired_time is null",
        nativeQuery = true)
    fun findAllActiveSessions(): List<SessionEntity>

}