package org.radhe.transaction_logger.accounts.domain.repository

import org.radhe.transaction_logger.accounts.domain.vo.AccountVo
import org.radhe.transaction_logger.sessions.domain.vo.AccountId

@ConsistentCopyVisibility
data class AccountModel private constructor(
    val id: AccountId? = null,
    val accountName: String,
    val description: String,
    val currency: Currency,
    val accountType: AccountType,
    val email: Email? = null
) {
    companion object {
        fun createAccount(accountVo: AccountVo): AccountModel {
            return AccountModel(
                accountName = accountVo.accountName,
                description = accountVo.description,
                currency = accountVo.currency,
                accountType = accountVo.accountType,
                email = accountVo.email,
            )
        }
    }
}

@JvmInline
value class Email(val value: String) {
    init {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        if (!emailRegex.matches(value)) {
            throw IllegalArgumentException("Invalid email format")
        }
    }
}

enum class Currency {
    THB, INR
}

enum class AccountType {
    SAVING, LOAN, CURRENT
}
