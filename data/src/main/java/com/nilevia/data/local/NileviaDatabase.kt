package com.nilevia.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nilevia.data.local.dao.CityDAO
import com.nilevia.data.models.CityDTO

@Database(
    entities = [
        CityDTO::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NileviaDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDAO

    companion object {
        private const val DATABASE_NAME = "nilevia_db"
        private val LOCK = Any()
        private var sInstance: NileviaDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): NileviaDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        NileviaDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance!!
        }
    }

}
