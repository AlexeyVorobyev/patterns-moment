package org.lexxv.strategy.studentfileprocessing

import org.lexxv.student.Student

/**
 * Интерфейс для классов, предназначенных для реализации работы с разным видом записи и чтения из файлов
 */
interface StudentFileProcessorInterface {
    fun read_from_file(filePath: String): MutableList<Student>

    fun write_to_file(students: MutableList<Student>, directory: String, fileName: String)

}