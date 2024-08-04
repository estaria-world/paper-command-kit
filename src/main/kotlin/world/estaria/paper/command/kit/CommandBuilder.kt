package world.estaria.paper.command.kit

import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.paper.LegacyPaperCommandManager

/**
 * @author Niklas Nieberler
 */

class CommandBuilder(
    commandManager: LegacyPaperCommandManager<CommandSender>
) {

    private val annotationParser = AnnotationParser(commandManager, CommandSender::class.java)

    /**
     * Parse a new cloud command
     * @param command instance of the command class
     */
    fun <T : Any> parseCommand(command: T): CommandBuilder {
        this.annotationParser.parse(command)
        return this
    }

}