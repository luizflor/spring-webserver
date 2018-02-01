package net.corda.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * TODO: DOCUMENT THIS
 */
@Component
class Configuration @Autowired private constructor(
        @Value("\${config.rpc.host}") val host: String,
        @Value("\${config.rpc.username}") val username: String,
        @Value("\${config.rpc.password}") val password: String,
        @Value("\${config.rpc.port}") val rpcPort: Int)