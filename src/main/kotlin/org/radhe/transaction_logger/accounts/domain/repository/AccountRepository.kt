package org.radhe.transaction_logger.accounts.domain.repository

import org.radhe.transaction_logger.accounts.infrastructure.persistence.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, String> {

}