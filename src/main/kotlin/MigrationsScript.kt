package org.lexxv

/**
 * Вспомогательный скрипт для запуска и отката миграций.
 * Вбирает в себя набор дополнительных рефлексивных функций и инициализацию скрипта.
 */

import org.lexxv.db.Migration
import org.lexxv.db.MigrationList
import org.lexxv.db.migrationstore.JsonMigrationStore
import org.lexxv.db.migrationstore.MigrationStoreInterface
import java.io.File
import kotlin.reflect.full.createInstance

/**
 * Получаем хранилище миграций
 */
fun getStore(): MigrationStoreInterface {
    return JsonMigrationStore()
}

/**
 * Получаем список всех неиспользованных миграций из папки с файлами в виде объектов Migration.
 *
 * @param migrationsPath - Путь до папки
 * @param excludedClasses - список наименований использованных миграций
 */
fun getUnusedClassesForUp(
    migrationsPath: String,
    excludedClasses: List<String>
): MutableList<Migration> {
    val unusedClasses = mutableListOf<Migration>()
    val migrationsDir = File(migrationsPath)

    if (migrationsDir.exists() && migrationsDir.isDirectory) {
        migrationsDir.walk().filter { it.isFile && it.name.endsWith(".kt") }.forEach { file ->
            var className = file.nameWithoutExtension
            println(className)
            if (!excludedClasses.contains(className)) {
                className = "org.lexxv.migrations.$className"
                println(className)
                val kClass = Class.forName(className).kotlin
                val instance = kClass.createInstance() // Проверяем, что класс можно проинстанциировать
                unusedClasses.add(instance as Migration)
            } else {
                println("excluded class: $className")
            }
        }
    } else {
        println("dir not exist")
    }

    return unusedClasses
}

/**
 * Инициализация объектов Migration для отката определённого кол-ва миграций.
 */
fun initInstancesForDown(count: Int): List<Migration> {
    val migrationList = getStore().getAlreadyUpMigrationNames().takeLast(count)
    return migrationList.asSequence()
        .map { migrationName ->
            val className = "org.lexxv.migrations." + migrationName
            val kClass = Class.forName("$className").kotlin
            kClass.createInstance() as Migration
        }
        .toList()
}

/**
 * Полный скрипт для запуска миграций.
 */
fun scriptForUp() {
    val classes = getUnusedClassesForUp("src/main/kotlin/migrations", getStore().getAlreadyUpMigrationNames())
    println(classes)
    val migrationList = MigrationList(classes)
    migrationList.allUp()
}

/**
 * Полный скрипт для отката миграций.
 */
fun scriptForDown(count: Int) {
    val migrationList = MigrationList(initInstancesForDown(count))
    migrationList.allDown(count)
}

/**
 * Точка входа, прописываем нужный скрипт и запускаем
 */
fun main() {
    scriptForUp()
    // scriptForDown(1)
}
