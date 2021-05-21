package me.gyanesh.hdp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.gyanesh.hdp.data.model.TestReport

@Dao
interface TestReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reports: List<TestReport>)

    @Query("SELECT * FROM TestReport")
    fun reports(): LiveData<List<TestReport>>

    @Query("DELETE FROM TestReport")
    fun clear()

}