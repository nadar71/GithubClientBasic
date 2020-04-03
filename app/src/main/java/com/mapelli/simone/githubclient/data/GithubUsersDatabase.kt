package com.mapelli.simone.githubclient.data

import android.content.Context
import android.util.Log

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import com.mapelli.simone.githubclient.data.entity.UserRepository

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserRepository::class, UserProfile_Full::class, UserProfile_Mini::class],
        version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class GithubUsersDatabase : RoomDatabase() {

    abstract fun githubUsersDao(): GithubUsersDbDao

    companion object {
        private val TAG = GithubUsersDatabase::class.java.getSimpleName()

        // lock for synchro
        private val LOCK = Any()
        private val DBNAME = "GithubUsersDB"
        @Volatile private var sDbInstance: GithubUsersDatabase? = null


        /*
        fun getsDbInstance(context: Context): GithubUsersDatabase {
            return sDbInstance ?: synchronized(this) {
                sDbInstance ?: buildDb(context).also { sDbInstance = it }
            }
        }

        private fun buildDb(context: Context) : GithubUsersDatabase{
            return Room.databaseBuilder<GithubUsersDatabase>(context.applicationContext,
                    GithubUsersDatabase::class.java, DBNAME)
                    .build()
        }
        */

        // BEFORE :
        fun getsDbInstance(context: Context): GithubUsersDatabase? {
            if (sDbInstance == null) {
                synchronized(this) {
                    Log.d(TAG, "Creating App db singleton instance...")
                    sDbInstance = Room.databaseBuilder<GithubUsersDatabase>(context.applicationContext,
                            GithubUsersDatabase::class.java, GithubUsersDatabase.DBNAME)
                            .build()
                }

            }
            Log.d(TAG, "Db created")
            return sDbInstance
        }


    }


}
