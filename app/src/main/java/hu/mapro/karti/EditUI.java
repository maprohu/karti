package hu.mapro.karti;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import java.util.Observable;
import java.util.Observer;

public class EditUI {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    final RecorderWidget questionRecorder;
    final RecorderWidget answerRecorder;
    final EditText questionText;
    final EditText answerText;
    final Button saveButton;
    final Button cancelButton;

    public EditUI(final Activity context) {
        ActivityCompat.requestPermissions(
                context,
                new String[] {Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO_PERMISSION
        );

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        questionText = new EditText(context);
        questionRecorder = new RecorderWidget(context);
        answerRecorder = new RecorderWidget(context);
        answerText = new EditText(context);

        LinearLayout.LayoutParams fixParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        params.weight = 1;

        Toolbar toolbar = new Toolbar(context);
        toolbar.setBackgroundColor(Color.CYAN);
        toolbar.setTitle("Create Karti");
        toolbar.setElevation(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics())
        );

        Toolbar.LayoutParams saveParams = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
        );
        saveParams.gravity = Gravity.END;
        cancelButton = new Button(context);
        cancelButton.setText("Cancel");
        saveButton = new Button(context);
        saveButton.setText("Save");
        toolbar.addView(saveButton, saveParams);
        toolbar.addView(cancelButton, saveParams);

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.finish();
                    }
                }
        );

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(toolbar);
        layout.addView(questionText, params);
        layout.addView(questionRecorder.widget, fixParams);
        layout.addView(answerText, params);
        layout.addView(answerRecorder.widget, fixParams);

        context.setContentView(layout, layoutParams);

        Observer saveObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                saveButton.setEnabled(
                        !questionRecorder.recording
                                && !answerRecorder.recording
                );
            }
        };
        questionRecorder.addObserver(saveObserver);
        answerRecorder.addObserver(saveObserver);
        saveObserver.update(null, null);

    }

    void close() {
        questionRecorder.close();
        answerRecorder.close();
    }


}

