package world.estaria.paper.command.kit.exception.local

import world.avionik.configkit.ConfigLoader
import world.avionik.configkit.format.YamlFileFormatter
import world.estaria.paper.command.kit.config.Config
import java.io.File

/**
 * @author Niklas Nieberler
 */

class ConfigLoader : ConfigLoader<Config>(
    File("plugins/paperCommands/config.yaml"),
    YamlFileFormatter(
        Config.serializer()
    ),
    { Config.Default.get() }
)