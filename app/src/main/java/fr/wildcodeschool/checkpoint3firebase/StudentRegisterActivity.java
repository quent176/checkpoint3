package fr.wildcodeschool.checkpoint3firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegisterActivity extends AppCompatActivity {

    String mUID;
    EditText mStudentRegisterFirstName, mStudentRegisterLastName;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_create);

        mStudentRegisterFirstName = (EditText) findViewById(R.id.studentregister_firstname);
        mStudentRegisterLastName = (EditText) findViewById(R.id.studentregister_lastname);
        Button buttonRegister = (Button) findViewById(R.id.button_register);

        // Write a message to the database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(getResources().getString(R.string.firebase_user)).child("students");
        mUID = mDatabaseReference.push().getKey();
        mDatabaseReference.child(mUID);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentModel studentModel = new StudentModel(mStudentRegisterFirstName.getText().toString(), mStudentRegisterLastName.getText().toString(), "0");
                mDatabaseReference.push().setValue(studentModel);
                Toast.makeText(StudentRegisterActivity.this,getString(R.string.add_student),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
