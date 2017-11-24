package hu.mapro.karti;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

/**
 * Created by maprohu on 24-11-2017.
 */

public class RecorderWidget extends Observable {

    final View widget;
    final File file;
    final Button playButton;
    boolean recorded = false;
    private final MediaRecorder recorder;
    boolean recording = false;

    public RecorderWidget(Context context) {
        Toolbar toolbar = new Toolbar(context);
        widget = toolbar;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;

        playButton = new Button(context);
        playButton.setText("Play");
        playButton.setEnabled(false);
        layout.addView(playButton, params);

        final Button recordButton = new Button(context);
        recordButton.setText("Record");
        layout.addView(recordButton, params);

        Toolbar.LayoutParams toolbarParams = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT,
                Gravity.FILL
        );
        toolbar.addView(layout, toolbarParams);
        toolbar.getMenu().add("Copy");
        toolbar.getMenu().add("Paste");

        try {
            file = File.createTempFile(
                    "recording",
                    ".m4a",
                    context.getExternalCacheDir()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        playButton.setOnClickListener(
                new View.OnClickListener() {
                    MediaPlayer player;

                    void destroyPlayer() {
                        if (player != null) {
                            player.stop();
                            player.release();
                            player = null;
                            playButton.setText("Play");
                        }
                    }

                    @Override
                    public void onClick(View v) {
                        try {
                            if (player == null) {
                                player = new MediaPlayer();
                                player.setDataSource(file.getAbsolutePath());
                                player.prepare();
                                player.start();
                                playButton.setText("Playing...");

                                player.setOnCompletionListener(
                                        new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mp) {
                                                destroyPlayer();
                                            }
                                        }
                                );
                            } else {
                                destroyPlayer();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }
        );

        recorder = new MediaRecorder();

        recordButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!recording) {
                            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                            recorder.setAudioChannels(1);
                            recorder.setAudioEncodingBitRate(16 * 44100);
                            recorder.setAudioSamplingRate(44100);
                            recorder.setOutputFile(file.getAbsolutePath());
                            try {
                                recorder.prepare();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            recorder.start();
                            recording = true;
                            recordButton.setText("Recording");
                            playButton.setEnabled(false);
                            setChanged();
                            notifyObservers();
                        } else {
                            recorder.stop();
                            recording = false;
                            recordButton.setText("Record");
                            recorded = true;
                            playButton.setEnabled(true);
                            setChanged();
                            notifyObservers();
                        }
                    }
                }
        );


    }

    void close() {
        try {
            recorder.stop();
        } catch (Exception e) {}
        try {
            recorder.release();
        } catch (Exception e) {}
        try {
            file.delete();
        } catch (Exception e) {}
    }
}
