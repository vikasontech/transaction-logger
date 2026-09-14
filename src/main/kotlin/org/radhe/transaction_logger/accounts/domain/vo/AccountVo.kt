package org.radhe.transaction_logger.accounts.domain.vo

import org.radhe.transaction_logger.accounts.domain.repository.AccountModel
import org.radhe.transaction_logger.accounts.domain.repository.AccountType
import org.radhe.transaction_logger.accounts.domain.repository.Currency
import org.radhe.transaction_logger.accounts.domain.repository.Email
import org.radhe.transaction_logger.accounts.infrastructure.persistence.AccountEntity
import java.util.UUID

data class AccountVo(
    val accountName: String,
    val description: String,
    val currency: Currency,
    val accountType: AccountType,
    val email: Email?
) {
    fun entity(): AccountEntity{
        return AccountEntity(
            id = UUID.randomUUID().toString(),
            accountName = accountName,
            description = description,
            currency = currency,
            accountType = accountType,
            email = email,
        )
    }
}