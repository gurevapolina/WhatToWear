package edu.adm.spbstu.whattowear;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView departmentName;
    private TextView universityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.view);
        departmentName = findViewById(R.id.departmentName);
        universityName = findViewById(R.id.universityName);
        constraintLayout.setBackgroundColor(Color.rgb(0,0, 0));

    }

    public void onTheSiteButtonClicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://amd.spbstu.ru"));
        startActivity(intent);
    }

    public void startClicked(View v) {
        Intent intent = new Intent(this, ClosetActivity.class);
        startActivity(intent);
    }
}
