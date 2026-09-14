//package org.radhe.transaction_logger.transactions.api
//
//import org.radhe.transaction_logger.sessions.application.SessionUseCases
//import org.radhe.transaction_logger.sessions.domain.vo.AccountId
//import org.radhe.transaction_logger.sessions.domain.vo.SessionPeriod
//import org.radhe.transaction_logger.transactions.application.TransactionsUseCase
//import org.springframework.shell.core.command.annotation.Argument
//import org.springframework.shell.core.command.annotation.Command
//import org.springframework.stereotype.Component
//
//@Component
//class CreateTransactionsCommand(
//    private val transactionsUseCase: TransactionsUseCase
//) {
//    @Command(
//        name = ["ct"],
//        description = "create transaction logs"
//    )
//    fun createSession(
//        @Argument(index = 0) year: Int,
//        @Argument(index = 1) month: Int,
//        @Argument(index = 2) accountId: Long
//    ): String {
//        try {
////            val sessionPeriod = SessionPeriod(year, month)
//            val handle = transactionsUseCase.handle()
//            return "Transaction created: ${handle}"
//        } catch (x: Exception) {
//            return "Error: ${x.message}"
//        }
//    }
//}
//
//
