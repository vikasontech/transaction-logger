package org.radhe.transaction_logger.sessions.domain.vo

data class SessionPeriod(val year: Int, val month: Int) {
    init {
        if (year !in 2001..2027) throw InvalidFieldException("session.year.invalid", arrayOf(year))
        if (month !in 1..12) throw InvalidFieldException("session.month.invalid", arrayOf(month))
    }
}

class InvalidFieldException(
    val code: String,
    val args: Array<Any?> = emptyArray()
) : RuntimeException()
