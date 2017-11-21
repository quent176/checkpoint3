package fr.wildcodeschool.checkpoint3firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StudentInfosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_infos);

        Intent intent = getIntent();
        final String studentKey = intent.getStringExtra("studentKey");

        // TODO
    }
}
