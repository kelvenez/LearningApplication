package com.example.studytoworld.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.studytoworld.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class CreateLearningSchedule extends AppCompatActivity {
    private EditText dateTextView;
    private EditText timeTextView;
    private DatePickerDialog picker;
    private Button create;
    String subject;
    int day;
    int month;
    int year;
    int hour;
    int minute;
    String[] items = {"Chinese", "English", "Mathematics"};
    AutoCompleteTextView subjectTextView;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_learning_schedule);

        dateTextView = findViewById(R.id.date);
        timeTextView = findViewById(R.id.time);
        subjectTextView = findViewById(R.id.subject);
        adapterItems = new ArrayAdapter<String>(this,R.layout.subject_item,items);
        subjectTextView.setAdapter(adapterItems);
        create = findViewById(R.id.create_schedule);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calender = Calendar.getInstance();
                day = calender.get(Calendar.DAY_OF_MONTH);
                month = calender.get(Calendar.MONTH);
                year = calender.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(CreateLearningSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateTextView.setText(day +"/" + (month+1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        //subjectTextView.setText(adapterItems.getItem(0).toString(),false);

        subjectTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                subject = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Subject: " + subject, Toast.LENGTH_SHORT).show();
            }
        });
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createSchedule();
            }
        });
    }

    private void createSchedule() {
        //String subject, date,time;
        //subject = subjectTextView.getText().toString();
        //date = dateTextView.getText().toString();
        //time = timeTextView .getText().toString();

        Intent intent = getIntent();
        String password=intent.getStringExtra("password");

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        //Query checkUser = reference.orderByChild("password").equalTo(password);
        Query checkUser = reference.orderByChild("password").equalTo("abc12345678");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //DatabaseReference newScheduleRef = reference.child(password).child("Schedules");
                    DatabaseReference newScheduleRef = reference.child("abc12345678").child("Schedules");
                    Query largestId = newScheduleRef.orderByChild("id").limitToLast(1);
                    largestId.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int id = 0;
                                for (DataSnapshot snapshotChildren : snapshot.getChildren()) {
                                    if (snapshotChildren.child("id").getValue(int.class) > id) {
                                        id = snapshotChildren.child("id").getValue(int.class);
                                    }
                                }
                                ++id;
                                newScheduleRef.child(Integer.toString(id)).setValue(new Schedule(id, subject, year, month, day, hour, minute));
                            } else {
                                newScheduleRef.child(Integer.toString(0)).setValue(new Schedule(0, subject, year, month, day, hour, minute));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //int id =snapshot.child("id").getValue(int.class)+1;
                    //newScheduleRef.child(Integer.toString(id)).setValue(new Schedule(id, subject,year,month,day,hour,minute));
                    //newScheduleRef.child(Integer.toString(0)).setValue(new Schedule(0, subject,year,month,day,hour,minute));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeTextView.setText(String.format(Locale.getDefault(),"%02d:%02d", hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour , minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }
}