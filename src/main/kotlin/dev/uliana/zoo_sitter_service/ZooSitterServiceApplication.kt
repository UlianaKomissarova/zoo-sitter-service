package dev.uliana.zoo_sitter_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZooSitterServiceApplication

fun main(args: Array<String>) {
	runApplication<ZooSitterServiceApplication>(*args)
}