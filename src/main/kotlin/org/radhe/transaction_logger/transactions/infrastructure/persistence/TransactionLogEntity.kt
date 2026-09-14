package org.radhe.transaction_logger.transactions.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.radhe.transaction_logger.transactions.domain.model.TransactionLog
import org.radhe.transaction_logger.transactions.domain.model.TransactionType
import org.springframework.transaction.interceptor.TransactionAttribute
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "transaction_log")
data class TransactionLogEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val accountId: String,
    val transactionDate: LocalDate,
    val amount: BigDecimal,
    val tag: String,
    @Enumerated(EnumType.STRING)
    val transactionType: TransactionType,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val description: String
)

