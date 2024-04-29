package world.estaria.paper.command.kit.exception.kubernetes

import io.fabric8.kubernetes.client.KubernetesClientBuilder
import world.estaria.kube.configmap.kit.KubeConfigMapKit
import world.estaria.paper.command.kit.config.Config

/**
 * @author Niklas Nieberler
 */

class ConfigMapHandler {

    private val kubernetesClient = KubernetesClientBuilder().build()

    private val configSerializer = Config.serializer()
    private val configName = "paper-commands.yaml"
    private val configMapManager = KubeConfigMapKit.initializeKubeConfig("strela-system", kubernetesClient)

    init {
        if (!this.configMapManager.existsConfig(configName)) {
            this.configMapManager.createConfigMap(configName, configSerializer, Config.Default.get())
        }
    }

    fun updateConfig() {
        this.configMapManager.updateConfig(this.configName)
    }

    fun getConfig(): Config {
        return this.configMapManager.getConfig(this.configName, this.configSerializer)
            ?: throw NullPointerException("failed to find $configName")
    }

}