package org.radhe.transaction_logger.accounts.application

import org.radhe.transaction_logger.accounts.domain.vo.AccountVo
import org.radhe.transaction_logger.accounts.infrastructure.persistence.AccountEntity
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import java.util.Optional

interface AccountUseCase{
    fun createAccount(accountVo: AccountVo): AccountEntity
    fun getAllAccounts(): List<AccountEntity>?
    fun updateAccount (accountId: AccountId, accountVo: AccountVo): AccountEntity?
    fun getAccount(accountId: AccountId): Optional<AccountEntity>
}