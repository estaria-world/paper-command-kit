package world.estaria.paper.command.kit.config

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.Component
import world.avionik.minecraft.common.extension.text

/**
 * @author Niklas Nieberler
 */

@Serializable
class Config(
    val prefix: String,
    private val messages: List<CommandMessage>
) {

    object Default {
        fun get(): Config {
            return Config(
                "",
                CommandMessage.Type.entries.map { CommandMessage(it, "message") }
            )
        }
    }

    fun getMessage(type: CommandMessage.Type): Component {
        val message = this.messages.first { it.type == type }
        return text("$prefix $message")
    }

}