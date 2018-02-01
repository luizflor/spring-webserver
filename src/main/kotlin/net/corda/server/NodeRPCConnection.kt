package net.corda.server

import net.corda.client.rpc.CordaRPCClient
import net.corda.client.rpc.CordaRPCConnection
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.NetworkHostAndPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * Wraps a node RPC proxy.
 *
 * The RPC proxy is configured based on the properties in `application.properties`.
 *
 * @param config The RPC configuration object.
 * @property rpcConnection The RPC connection.
 * @property proxy The RPC proxy.
 */
@Component
class NodeRPCConnection @Autowired internal constructor(private val config: Configuration): AutoCloseable {
    lateinit var rpcConnection: CordaRPCConnection
        private set
    lateinit var proxy: CordaRPCOps
        private set

    @PostConstruct
    fun initialiseNodeRPCConnection() {
            val rpcAddress = NetworkHostAndPort(config.host, config.rpcPort)
            val rpcClient = CordaRPCClient(rpcAddress)
            val rpcConnection = rpcClient.start(config.username, config.password)
            proxy = rpcConnection.proxy
    }

    @PreDestroy
    override fun close() {
        rpcConnection.notifyServerAndClose()
    }
}