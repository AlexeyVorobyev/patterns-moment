package org.korsnaike.logger.outputmethod

class ConsoleLog: LogOutputMethod {
    override fun log(message: String) {
        println(message)
    }
}