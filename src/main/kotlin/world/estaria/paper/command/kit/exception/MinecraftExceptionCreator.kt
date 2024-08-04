package world.estaria.paper.command.kit.exception

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.incendo.cloud.exception.ArgumentParseException
import org.incendo.cloud.exception.InvalidSyntaxException
import org.incendo.cloud.exception.NoPermissionException
import org.incendo.cloud.exception.handling.ExceptionContext
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler
import org.incendo.cloud.paper.LegacyPaperCommandManager
import world.avionik.minecraft.common.extension.text
import world.estaria.translation.api.extension.translate
import world.estaria.translation.api.namespace.TranslationService
import world.estaria.translation.api.placeholder.Placeholder
import world.estaria.translation.api.registry.GlobalTranslator
import java.util.*

/**
 * @author Niklas Nieberler
 */

class MinecraftExceptionCreator {

    fun create(commandManager: LegacyPaperCommandManager<CommandSender>) {
        MinecraftExceptionHandler.createNative<CommandSender>()
            .handler(NoPermissionException::class.java, MinecraftExceptionHandler.createDefaultNoPermissionHandler())
            .handler(ArgumentParseException::class.java) { _, exception -> getComponentCommandUsage(exception) }
            .handler(InvalidSyntaxException::class.java) { _, exception -> getComponentCommandUsage(exception) }
            .registerTo(commandManager)
    }

    private fun getComponentCommandUsage(context: ExceptionContext<CommandSender, out Exception>): Component {
        val contextSender = context.context().sender()
        val exceptionMessageSplit = context.exception().message!!.split(":")

        val commandUsage = exceptionMessageSplit[1]
            .replace("<", "(")
            .replace(">", ")")
            .replaceFirst(" ", "")

        return if (commandUsage.contains("|")) {
            getMoreThanOneCommandUsage(contextSender, commandUsage)
        } else {
            contextSender.translate(
                "exceptionCommands",
                "command.tried.this",
                Placeholder.parsed("command", commandUsage)
            ).get() // is not the best :/
        }
    }

    private fun getMoreThanOneCommandUsage(audience: Audience, message: String): Component {
        val prefix = GlobalTranslator.translate("global", "prefix", Locale.US)?.toPattern()
        val fancyMessage = message.replace("|", "<#fef3c7> | <#fffbeb>")
        val namespace = TranslationService.fromNamespace("exceptionCommands")
        return namespace.translate("command.more.than.one", audience).get()
            .append(text("\n"))
            .append(text("$prefix <#fffbeb>$fancyMessage"))
    }

}