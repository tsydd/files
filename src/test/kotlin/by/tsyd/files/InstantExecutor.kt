package by.tsyd.files

import java.util.concurrent.Executor

object InstantExecutor : Executor {
    override fun execute(command: Runnable) = command.run()
}