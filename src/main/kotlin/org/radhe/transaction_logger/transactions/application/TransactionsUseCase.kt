package org.radhe.transaction_logger.transactions.application

import org.radhe.transaction_logger.accounts.application.AccountUseCase
import org.radhe.transaction_logger.sessions.application.SessionUseCases
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.radhe.transaction_logger.sessions.domain.vo.SessionPeriod
import org.radhe.transaction_logger.tags.application.TagsUseCase
import org.radhe.transaction_logger.tags.domain.vo.TagVo
import org.radhe.transaction_logger.tags.infrastructure.persistence.TagsEntity
import org.radhe.transaction_logger.transactions.api.TransactionLogRequest
import org.radhe.transaction_logger.transactions.domain.model.TransactionLog
import org.radhe.transaction_logger.transactions.domain.repository.TransactionLogRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

interface TransactionsUseCase {
    fun createTransaction(request: TransactionLogRequest): Long
}

@Component
class TransactionsUseCaseImpl(
    private val sessionUseCases: SessionUseCases,
    private val tagsUseCase: TagsUseCase,
    private val transactionLogRepo: TransactionLogRepo

    ): TransactionsUseCase {
    companion object {
        private val log = LoggerFactory.getLogger(TransactionsUseCase::class.java)
    }
    /**
     * 1. Get Active session
     * 2. get tag details,
     * 3. Add transactions
     */
    override fun createTransaction(request: TransactionLogRequest): Long {

        val activeSession = sessionUseCases.getActiveSession(request.sessionId)

        val tag: TagsEntity = tagsUseCase.getTag(request.tag)
            ?:tagsUseCase.handle(TagVo(name = request.tag, description = request.tag))

        val transactionLog: TransactionLog = TransactionLog.create(
            accountId = AccountId(value=activeSession.accountId),
            sessionPeriod = SessionPeriod(year = activeSession.year, month = activeSession.month),
            tag = tag.toDomain(),
            transactionType = request.transactionType,
            day = request.day,
            description = request.description,
            amount = request.amount,
        )
        val transactionLogEntity = transactionLogRepo.save(transactionLog.toEntity())
        log.info("New Transaction added: ${transactionLogEntity.id}")
        return transactionLogEntity.id!!
    }
}
