package org.radhe.transaction_logger.sessions.application

import org.radhe.transaction_logger.accounts.application.AccountUseCase
import org.radhe.transaction_logger.sessions.domain.model.Session
import org.radhe.transaction_logger.sessions.domain.repository.SessionRepository
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.radhe.transaction_logger.sessions.domain.vo.CreateSessionRequest
import org.radhe.transaction_logger.sessions.domain.vo.SessionId
//import org.radhe.transaction_logger.sessions.domain.vo.CreateSessionRequest
import org.radhe.transaction_logger.sessions.domain.vo.SessionVo
import org.radhe.transaction_logger.sessions.infrastructure.persistence.SessionEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

interface SessionUseCases {
    fun create(createSessionRequest: CreateSessionRequest): SessionVo
    fun closeSession(sessionId: SessionId)
    fun getActiveSession(sessionId: SessionId): SessionEntity
    fun listAllActiveSessions(): List<SessionEntity>
}
interface SessionPolicies {
    fun onlyOneActiveSessionPolicy(session: Session)
}

@Component
class SessionPoliciesImpl(
    val sessionRepository: SessionRepository,

) : SessionPolicies {

   companion object {
       private val log = LoggerFactory.getLogger(SessionPoliciesImpl::class.java)
   }
    override fun onlyOneActiveSessionPolicy(
        session: Session
    ) {
        val sessionPeriod = session.sessionPeriod
        val accountId = session.accountId
        log.info("Session creation policy on $sessionPeriod, $accountId")
        if (sessionRepository.findByAccountIdAndExpiryTime(accountId.value,
            sessionPeriod.year, sessionPeriod.month).isNotEmpty()) {
            throw AlreadyActiveSessionPresent()
        }
    }
}

class AlreadyActiveSessionPresent: RuntimeException("Only one active session policy voilated")

@Component
class SessionUseCasesImpl(
    private val sessionRepository: SessionRepository,
    private val accountUseCase: AccountUseCase,
    private val sessionPolicies:  SessionPolicies
) : SessionUseCases {

    override fun getActiveSession(sessionId: SessionId): SessionEntity {
        val findByAccountIdAndExpiryTime = sessionRepository.findBySessionIdAndExpiryTime(sessionId.value)
        if (findByAccountIdAndExpiryTime.isEmpty()  ) {
           throw NoActiveSessionFound()
        }
        if (findByAccountIdAndExpiryTime.size > 1  ) {
            throw MoreThanOneActiveSessionFound()
        }
        return findByAccountIdAndExpiryTime[0];
    }

    override fun listAllActiveSessions(): List<SessionEntity> {
        return sessionRepository.findAllActiveSessions()
    }

    override fun create(createSessionRequest: CreateSessionRequest): SessionVo {

        accountUseCase.getAccount(createSessionRequest.accountId)
            .orElseThrow({ IllegalArgumentException("Account with id ${createSessionRequest.accountId} does not exists") })

        val session = Session.open(createSessionRequest)
        sessionPolicies.onlyOneActiveSessionPolicy(session);
        val dbEntity = sessionRepository.save(session.entity())
        return dbEntity.toDomain();
    }

    override fun closeSession(sessionId: SessionId) {
        val sessionDetails = sessionRepository.findById(sessionId.value)
            .orElseThrow({ IllegalArgumentException("Session does not exist") })
            .toDomain()
        val session = Session.createSession(sessionDetails)
        val closedSession = session.close()
        sessionRepository.save(closedSession.entity())
    }
}