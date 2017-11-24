package hu.mapro.karti.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by maprohu on 24-11-2017.
 */

@Database(entities = {Recording.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecordingDao recordingDao();

}
