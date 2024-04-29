package world.estaria.paper.command.kit

import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.PaperCommandManager
import world.estaria.paper.command.kit.exception.ExceptionType

/**
 * @author Niklas Nieberler
 */

object PaperCommandKit {

    /**
     * Creates a new instance of [CommandBuilder]
     * @param javaPlugin to initialize the cloud commands
     * @param exceptionType where should the exception messages come from
     */
    fun create(javaPlugin: JavaPlugin, exceptionType: ExceptionType = ExceptionType.KUBERNETES) {
        val commandManager = PaperCommandManager.createNative(
            javaPlugin,
            ExecutionCoordinator.simpleCoordinator()
        )
        commandManager.registerBrigadier()
        commandManager.registerAsynchronousCompletions()
        exceptionType.executeExceptionHandler(commandManager)
        return
    }

}