package fr.wildcodeschool.checkpoint3firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentInfosActivity extends AppCompatActivity {

    TextView mStudentInfosFirstName;
    TextView mStudentInfosLastName;
    TextView mStudentInfosAverageValue;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    EditText mStudentInfosGradeValue;
    StudentModel mStudentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_infos);

        Intent intent = getIntent();
        final String studentKey = intent.getStringExtra("studentKey");

        mStudentInfosFirstName = (TextView) findViewById(R.id.studentinfos_firstname);
        mStudentInfosLastName = (TextView) findViewById(R.id.studentinfos_lastname);
        mStudentInfosAverageValue = (TextView) findViewById(R.id.studentinfos_average_value);
        mStudentInfosGradeValue = (EditText) findViewById(R.id.studentinfos_grade_value);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(getResources().getString(R.string.firebase_user)).child("students").child(studentKey);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mStudentModel = dataSnapshot.getValue(StudentModel.class);
                mStudentInfosFirstName.setText(mStudentModel.getFirstname());
                mStudentInfosLastName.setText(mStudentModel.getLastname());
                mStudentInfosAverageValue.setText(mStudentModel.getAverage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button studentInfosAddGrade = (Button) findViewById(R.id.studentinfos_add_grade);
        studentInfosAddGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double moyenneFirebase = Double.parseDouble(mStudentModel.getAverage());
                Double note = Double.parseDouble(mStudentInfosGradeValue.getText().toString());
                Double moyenneFinale = (moyenneFirebase + note) / 2;
                mDatabaseReference.child("average").setValue(String.valueOf(moyenneFinale));
                // mDatabaseReference.child("average").setValue(mStudentInfosGradeValue.getText().toString());
            }
        });

    }
}
