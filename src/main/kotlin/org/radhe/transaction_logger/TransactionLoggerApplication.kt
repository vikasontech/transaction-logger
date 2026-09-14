package org.radhe.transaction_logger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionLoggerApplication

fun main(args: Array<String>) {
	runApplication<TransactionLoggerApplication>(*args)
}
