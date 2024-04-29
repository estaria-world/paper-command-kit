package world.estaria.paper.command.kit.config

import kotlinx.serialization.Serializable

/**
 * @author Niklas Nieberler
 */

@Serializable
class CommandMessage(
    val type: Type,
    val message: String
) {

    enum class Type {
        ONE_COMMAND,

        MORE_COMMANDS
    }

}