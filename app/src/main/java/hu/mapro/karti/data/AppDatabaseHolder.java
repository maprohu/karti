package hu.mapro.karti.data;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by maprohu on 24-11-2017.
 */

public class AppDatabaseHolder {
    private AppDatabaseHolder() {}

    private static AppDatabase db = null;

    public static synchronized AppDatabase get(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "karti"
            ).build();
        }

        return db;
    }
}
