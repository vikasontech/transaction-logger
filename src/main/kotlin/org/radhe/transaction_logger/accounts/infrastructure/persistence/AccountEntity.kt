package org.radhe.transaction_logger.accounts.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.radhe.transaction_logger.accounts.domain.repository.AccountType
import org.radhe.transaction_logger.accounts.domain.repository.Currency
import org.radhe.transaction_logger.accounts.domain.repository.Email

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id
    val id: String? = null,
    val accountName: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val currency: Currency,
    @Enumerated(EnumType.STRING)
    val accountType: AccountType,
    val email: Email? = null
)