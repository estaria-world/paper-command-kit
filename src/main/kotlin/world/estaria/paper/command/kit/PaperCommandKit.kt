package world.estaria.paper.command.kit

import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.PaperCommandManager
import world.estaria.paper.command.kit.exception.MinecraftExceptionCreator
import world.estaria.translation.api.TranslationInitializer

/**
 * @author Niklas Nieberler
 */

object PaperCommandKit {

    /**
     * Creates a new instance of [CommandBuilder]
     * @param javaPlugin to initialize the cloud commands
     * @param tokenStorageType where should the GitHub token
     * @return new command builder instance
     */
    fun create(javaPlugin: JavaPlugin): CommandBuilder {
        val translationManager = TranslationInitializer("exceptionCommands", "paper")
            .initialize()

        val commandManager = PaperCommandManager.createNative(
            javaPlugin,
            ExecutionCoordinator.simpleCoordinator()
        )
        commandManager.registerBrigadier()
        commandManager.registerAsynchronousCompletions()
        MinecraftExceptionCreator(translationManager).create(commandManager)
        return CommandBuilder(commandManager)
    }

}