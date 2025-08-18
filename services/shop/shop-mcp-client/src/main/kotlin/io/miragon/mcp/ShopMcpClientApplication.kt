package io.miragon.mcp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShopMcpClientApplication

fun main(args: Array<String>) {
    runApplication<ShopMcpClientApplication>(*args)
}