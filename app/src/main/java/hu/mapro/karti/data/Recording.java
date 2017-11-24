package hu.mapro.karti.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by maprohu on 24-11-2017.
 */

@Entity
public class Recording {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] data;

}
