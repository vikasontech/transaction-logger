//package org.radhe.transaction_logger.sessions.api
//
//import org.radhe.transaction_logger.sessions.application.SessionUseCases
//import org.radhe.transaction_logger.sessions.domain.vo.AccountId
//import org.radhe.transaction_logger.sessions.domain.vo.SessionPeriod
//import org.springframework.shell.core.command.annotation.Argument
//import org.springframework.shell.core.command.annotation.Command
//import org.springframework.stereotype.Component
//
//@Component
//class CreateSessionCommand(
//    private val sessionUseCases: SessionUseCases
//) {
//    @Command(
//        name = ["create-session"],
//        description = "create transaction logs"
//    )
//    fun createSession(
//        @Argument(index = 0) year: Int,
//        @Argument(index = 1) month: Int,
//        @Argument(index = 2) accountId: Long
//    ): String {
//        try {
//            val sessionPeriod = SessionPeriod(year, month)
//            val handle = sessionUseCases.handle(sessionPeriod = sessionPeriod, accountId = AccountId(value = accountId))
//            return "Session created with id : ${handle}"
//        } catch (x: Exception) {
//            return "Error: ${x.message}"
//        }
//    }
//}
//
//
