package com.amir.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amir.todoapp.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("select * from todo_table order by id asc")
    fun getAllData(): LiveData<List<ToDoData>>
/*onConflict
when new data comes to our database,
  which is the same as item that we already have, we define a strategy what should our database do
 */
    /*suspend
    to tell the compiler that our function will be run inside of a coroutine
    it means we want to run our function in background
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    @Query("delete from todo_table ")
    suspend fun deleteAll()

    @Query("select * from todo_table where title like:searchQuery ")
    fun searchDatabase(searchQuery:String):LiveData<List<ToDoData>>

    @Query("select * from todo_table order by case when priority like 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun sortByHighPriority():LiveData<List<ToDoData>>

    @Query("select * from todo_table order by case when priority like 'L%' then 1 when priority like 'M%' then 2 when priority like 'H%' then 3 end")
    fun sortByLowPriority():LiveData<List<ToDoData>>

}