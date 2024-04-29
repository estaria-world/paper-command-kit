package world.estaria.paper.command.kit.exception

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.incendo.cloud.exception.ArgumentParseException
import org.incendo.cloud.exception.InvalidSyntaxException
import org.incendo.cloud.exception.NoPermissionException
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler
import org.incendo.cloud.paper.PaperCommandManager
import world.avionik.minecraft.common.extension.text
import world.estaria.paper.command.kit.config.CommandMessage
import world.estaria.paper.command.kit.config.Config

/**
 * @author Niklas Nieberler
 */

class MinecraftExceptionCreator(
    private val config: Config
)  {

    fun create(commandManager: PaperCommandManager<CommandSender>) {
        MinecraftExceptionHandler.createNative<CommandSender>()
            .handler(NoPermissionException::class.java) { MinecraftExceptionHandler.createDefaultNoPermissionHandler<CommandSender>().apply(it) }
            .handler(ArgumentParseException::class.java) { getComponentCommandUsage(it.exception()) }
            .handler(InvalidSyntaxException::class.java) { getComponentCommandUsage(it.exception()) }
            .registerTo(commandManager)
    }

    private fun getComponentCommandUsage(exception: Exception): Component {
        val exceptionMessageSplit = exception.message!!.split(":")
        val commandUsage = exceptionMessageSplit[1]
            .replace("<", "(")
            .replace(">", ")")
            .replaceFirst(" ", "")

        return if (commandUsage.contains("|")) {
            getMoreThanOneCommandUsage(commandUsage)
        } else {
            this.config.getMessage(CommandMessage.Type.ONE_COMMAND)
        }
    }

    private fun getMoreThanOneCommandUsage(message: String): Component {
        val fancyMessage = message.replace("|", "<#c7ecee> | <#dff9fb>")
        return this.config.getMessage(CommandMessage.Type.MORE_COMMANDS)
            .append(text("\n"))
            .append(text("${config.prefix} <#dff9fb>$fancyMessage"))
    }

}