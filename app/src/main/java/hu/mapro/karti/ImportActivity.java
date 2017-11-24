package hu.mapro.karti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImportActivity extends AppCompatActivity {

    private ImportUI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ui = new ImportUI(this);

        Uri uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);

        try {
            try (InputStream in = getContentResolver().openInputStream(uri)) {
                try (FileOutputStream out = new FileOutputStream(ui.answerRecorder.file)) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ui.answerRecorder.playButton.setEnabled(true);

        ui.saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

    }

    @Override
    protected void onDestroy() {
        ui.close();

        super.onDestroy();
    }
}
