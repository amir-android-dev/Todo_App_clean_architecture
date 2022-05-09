package com.amir.todoapp.data

import androidx.room.TypeConverter
import com.amir.todoapp.data.models.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        /*name
        returns the name of this enum constant, exactly as declared in its enum declarations
        it means it returns high, medium , low
         */
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}