package com.amir.todoapp.data

import android.content.Context
import androidx.room.*
import com.amir.todoapp.data.models.ToDoData

@Database(entities = [ToDoData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase : RoomDatabase() {
    //it will return the todoDAO
    abstract fun todoDao(): ToDoDao

    //companion object is the same as public static final class in java
    companion object {

        //volatile: writes to this field are immediately made visible for other threads
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {

                return tempInstance
            }
            /*synchronized
            when a thread calls a synchronized, it acquires the lock of that synchronized block.
            other threads don't have permission to call that same synchronized block as long as previous thread
            which has acquired the lock does not release the lock
             */
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}