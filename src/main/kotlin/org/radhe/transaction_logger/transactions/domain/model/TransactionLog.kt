package org.radhe.transaction_logger.transactions.domain.model

import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.radhe.transaction_logger.sessions.domain.vo.SessionPeriod
import org.radhe.transaction_logger.tags.domain.model.Tags
import org.radhe.transaction_logger.transactions.infrastructure.persistence.TransactionLogEntity
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime


@JvmInline
value class TransactionId(val value: Long) {}

@ConsistentCopyVisibility
data class TransactionLog private constructor(
    val id: TransactionId? = null,
    val accountId: AccountId,
    val sessionPeriod: SessionPeriod,
    val tag: Tags,
    val day: Day,
    val transactionType: TransactionType,
    val createdAt: LocalDateTime,
    val amount: BigDecimal,
    val description: String
) {


    companion object {

        fun dateShouldBeValidPolicy(year: Int, month: Int, day: Int) {
            LocalDate.of(year, month, day)
        }
        fun create(
            transactionId: TransactionId? = null,
            accountId: AccountId,
            sessionPeriod: SessionPeriod,
            tag: Tags,
            day: Day,
            transactionType: TransactionType,
            amount: BigDecimal,
            description: String
        ): TransactionLog {

            this.dateShouldBeValidPolicy(sessionPeriod.year,
                sessionPeriod.month,
                day.value)

            return TransactionLog(
                id = transactionId,
                accountId = accountId,
                sessionPeriod = sessionPeriod,
                tag = tag,
                day = day,
                transactionType = transactionType,
                amount = amount,
                description = description,
                createdAt = LocalDateTime.now()
            )
        }
    }

    fun toEntity(): TransactionLogEntity {
        return TransactionLogEntity(
            id = id?.value,
            accountId = accountId.value,
            transactionDate = LocalDate.of(
                sessionPeriod.year,
                sessionPeriod.month,
                day.value
            ),
            transactionType = transactionType,
            createdAt = createdAt,
            tag = tag.name,
            description = description,
            amount = amount
        )
    }

}

data class Day(val value: Int) {
    init {
        require(value in 0..31, { "Days should be in range 1 to 31" })
    }
}

enum class TransactionType {
    income, expense, transfer
}
