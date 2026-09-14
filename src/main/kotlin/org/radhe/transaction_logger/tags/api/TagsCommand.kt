//package org.radhe.transaction_logger.tags.api
//
//import org.radhe.transaction_logger.tags.application.TagsUseCase
//import org.radhe.transaction_logger.transactions.application.TransactionsUseCase
//import org.springframework.shell.core.command.annotation.Argument
//import org.springframework.shell.core.command.annotation.Command
//import org.springframework.stereotype.Component
//
//@Component
//class TagsCommand(
//    val tagsUseCase: TagsUseCase
//) {
//    @Command(
//        name = ["tags"],
//        description = "create tags"
//    )
//    fun createSession(
//        @Argument(index = 0) name: String,
//        @Argument(index = 1) description: String,
//    ): String {
//        try {
//            val handle = tagsUseCase.handle(name, description)
//            return "Tag created: ${handle}"
//        } catch (x: Exception) {
//            return "Error: ${x.message}"
//        }
//    }
//}
//
//
