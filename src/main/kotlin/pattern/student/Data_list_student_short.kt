package org.lexxv.pattern.student

import org.lexxv.pattern.Data_list
import org.lexxv.student.Student_short

class Data_list_student_short(students: List<Student_short>) : Data_list<Student_short>(students) {
    override fun getEntityFields(): List<String> {
        return listOf("Имя", "Гит", "Контакт")
    }

    override fun getDataRow(entity: Student_short): List<Any> {
        return listOf(entity.lastNameInitials, entity.git, entity.contact) as List<Any>
    }

}