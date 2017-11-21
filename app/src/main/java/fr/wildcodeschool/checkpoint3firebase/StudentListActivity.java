package fr.wildcodeschool.checkpoint3firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StudentListActivity extends AppCompatActivity {

    FirebaseRecyclerAdapter sAdapter = null;
    private ProgressDialog progress_dialog;
    private ProgressDialog progressTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        progress_dialog = new ProgressDialog(StudentListActivity.this);
        progress_dialog.setCancelable(false);
        progress_dialog.setMessage(getString(R.string.loading));
        progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress_dialog.show();

        /*progressTemp = new ProgressDialog(StudentListActivity.this);
        progressTemp.setCancelable(false);
        progressTemp.setMessage("Loading");
        progressTemp.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressTemp.show();*/

        Query query = FirebaseDatabase.getInstance()
                .getReference(getString(R.string.firebase_user) + "/students");

        FirebaseRecyclerOptions<StudentModel> options =
                new FirebaseRecyclerOptions.Builder<StudentModel>()
                        .setQuery(query, StudentModel.class)
                        .build();

        sAdapter = new FirebaseRecyclerAdapter<StudentModel, StudentHolder>(options) {
            @Override
            public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_student, parent, false);

                return new StudentHolder(view, new StudentHolderListener() {
                    @Override
                    public void onClick(int position) {
                        String studentKey = sAdapter.getRef(position).getKey();
                        Intent intent = new Intent(StudentListActivity.this, StudentInfosActivity.class);
                        intent.putExtra("studentKey", studentKey);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                progress_dialog.dismiss();
            }

            @Override
            protected void onBindViewHolder(StudentHolder holder, int position, StudentModel model) {
                holder.mPrenom.setText(model.getFirstname());
                holder.mLastname.setText(model.getLastname());
                holder.bind(position);
            }
        };
        RecyclerView studentList = findViewById(R.id.goto_studentlist);
        studentList.setHasFixedSize(true);
        studentList.setLayoutManager(new LinearLayoutManager(this));
        studentList.setAdapter(sAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sAdapter != null) {
            sAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sAdapter != null) {
            sAdapter.stopListening();
        }
    }

    public interface StudentHolderListener {
        public void onClick(int position);
    }

    public class StudentHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mPrenom;
        public TextView mLastname;
        public StudentHolderListener mListener;

        public StudentHolder(View view, final StudentHolderListener listener) {
            super(view);
            this.mView = view;
            this.mPrenom = view.findViewById(R.id.studentitem_firstname);
            this.mLastname = view.findViewById(R.id.studentitem_lastname);
            this.mListener = listener;
        }

        public void bind(final int position) {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(position);
                }
            });
        }
    }
}
