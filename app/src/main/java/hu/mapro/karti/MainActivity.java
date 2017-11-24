package hu.mapro.karti;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ImportUI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ui = new ImportUI(this);
        ui.answerText.setText(
                getExternalCacheDir().getAbsolutePath()
        );

    }

    @Override
    protected void onDestroy() {
        ui.close();

        super.onDestroy();
    }
}
