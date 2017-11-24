package hu.mapro.karti.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by maprohu on 24-11-2017.
 */

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = Recording.class,
                        parentColumns = "id",
                        childColumns = "questionRecording"
                ),
                @ForeignKey(
                        entity = Recording.class,
                        parentColumns = "id",
                        childColumns = "answerRecording"
                ),
        }
)
public class Card {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String questionText;

    public Long questionRecording;

    public String answerText;

    public Long answerRecording;

}
