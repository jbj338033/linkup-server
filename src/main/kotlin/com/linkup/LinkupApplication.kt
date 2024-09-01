package com.kakaotalk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KakaotalkApplication

fun main(args: Array<String>) {
    runApplication<KakaotalkApplication>(*args)
}
