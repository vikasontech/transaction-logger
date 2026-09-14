package org.radhe.transaction_logger.sessions.domain.vo


data class CreateSessionRequest(
    val accountId: AccountId,
    val sessionPeriod: SessionPeriod,
) {
}