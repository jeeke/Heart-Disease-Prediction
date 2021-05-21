package me.gyanesh.hdp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.gyanesh.hdp.data.model.TestReport

@Database(
    entities = [TestReport::class],
    version = 1,
    exportSchema = false
)
abstract class HDPDb : RoomDatabase() {

    companion object {
        fun create(context: Context): HDPDb {
            val databaseBuilder = Room.databaseBuilder(context, HDPDb::class.java, "hdp.db")
            return databaseBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun testReportDao(): TestReportDao
}