package world.estaria.paper.command.kit.exception

import org.bukkit.command.CommandSender
import org.incendo.cloud.paper.PaperCommandManager
import world.estaria.paper.command.kit.exception.kubernetes.ConfigMapHandler
import world.estaria.paper.command.kit.exception.local.ConfigLoader

/**
 * @author Niklas Nieberler
 */

enum class ExceptionType {

    KUBERNETES {
        override fun executeExceptionHandler(commandManager: PaperCommandManager<CommandSender>) {
            val configMapHandler = ConfigMapHandler()
            MinecraftExceptionCreator(configMapHandler.getConfig())
        }
    },

    LOCAL {
        override fun executeExceptionHandler(commandManager: PaperCommandManager<CommandSender>) {
            val config = ConfigLoader().load()
            MinecraftExceptionCreator(config).create(commandManager)
        }
    };

    abstract fun executeExceptionHandler(commandManager: PaperCommandManager<CommandSender>)

}