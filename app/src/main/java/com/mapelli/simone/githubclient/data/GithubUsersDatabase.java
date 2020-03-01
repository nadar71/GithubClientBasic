package com.mapelli.simone.githubclient.data;

import android.content.Context;
import android.util.Log;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserRepository.class, UserProfile_Full.class, UserProfile_Mini.class},
        version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class GithubUsersDatabase extends RoomDatabase {
    private static final String TAG = GithubUsersDatabase.class.getSimpleName();

    // lock for synchro
    private static final Object LOCK   = new Object();
    private static final String DBNAME = "GithubUsersDB";
    private static GithubUsersDatabase sDbInstance ;

    // get for the dao
    public abstract GithubUsersDbDao githubUsersDao();


    public static GithubUsersDatabase getsDbInstance(Context context){
        if(sDbInstance == null){
            synchronized (LOCK){
                Log.d(TAG, "Creating App db singleton instance...");
                sDbInstance = Room.databaseBuilder(context.getApplicationContext(), GithubUsersDatabase.class,GithubUsersDatabase.DBNAME)
                        //.allowMainThreadQueries() // TODO : temporary in case of debugging, delete this
                        .build();
            }

        }
        Log.d(TAG, "Db created");
        return sDbInstance;
    }


}
