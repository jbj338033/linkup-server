package com.linkup

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LinkupApplication

fun main(args: Array<String>) {
    runApplication<LinkupApplication>(*args)
}
