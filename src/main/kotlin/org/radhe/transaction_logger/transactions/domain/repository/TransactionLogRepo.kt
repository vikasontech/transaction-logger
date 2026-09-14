package org.radhe.transaction_logger.transactions.domain.repository

import org.radhe.transaction_logger.transactions.infrastructure.persistence.TransactionLogEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionLogRepo: JpaRepository<TransactionLogEntity, Long> {

}
