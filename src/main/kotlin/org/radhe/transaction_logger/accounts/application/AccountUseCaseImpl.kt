package org.radhe.transaction_logger.accounts.application

import org.radhe.transaction_logger.accounts.domain.repository.AccountRepository
import org.radhe.transaction_logger.accounts.domain.vo.AccountVo
import org.radhe.transaction_logger.accounts.infrastructure.persistence.AccountEntity
import org.radhe.transaction_logger.sessions.domain.vo.AccountId
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AccountUseCaseImpl(
    private val repository: AccountRepository
) : AccountUseCase {

    override fun createAccount(accountVo: AccountVo): AccountEntity {
        return repository.save(accountVo.entity())
    }

    override fun getAllAccounts(): List<AccountEntity>? {
        return repository.findAll()
    }

    override fun getAccount(accountId: AccountId): Optional<AccountEntity> {
        return repository.findById(accountId.value);
    }
    override fun updateAccount(
        accountId: AccountId,
        accountVo: AccountVo
    ): AccountEntity? {
        val accountEntity = repository.findById(accountId.value)
            .orElseThrow { IllegalArgumentException("Account with id $accountId not found") }

        return repository.save(
            accountEntity.copy(
                accountName = accountVo.accountName,
                description = accountVo.description,
                currency = accountVo.currency,
                accountType = accountVo.accountType,
                email = accountVo.email
            )
        )
    }
}