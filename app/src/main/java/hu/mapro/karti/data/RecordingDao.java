package hu.mapro.karti.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

/**
 * Created by maprohu on 24-11-2017.
 */

@Dao
public interface RecordingDao {

    @Insert
    long insert(Recording data);

}
