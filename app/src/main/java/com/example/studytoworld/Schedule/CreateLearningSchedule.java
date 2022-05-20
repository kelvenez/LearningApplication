package com.example.studytoworld.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.studytoworld.timer.AlarmReceiver;
import com.example.studytoworld.R;
import com.example.studytoworld.databinding.ActivityCreateLearningScheduleBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class CreateLearningSchedule extends AppCompatActivity {
    private EditText dateTextView;
    private Calendar timeCalendar;
    private EditText timeTextView;
    private DatePickerDialog picker;
    private AppCompatButton create;
    private ActivityCreateLearningScheduleBinding binding;
    String uid;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String subject;
    int day;
    int month;
    int year;
    int hour;
    int minute;
    String[] items = {"Chinese", "English"};
    AutoCompleteTextView subjectTextView;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        //setContentView(R.layout.activity_create_learning_schedule);

        binding = ActivityCreateLearningScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.createSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dateTextView = findViewById(R.id.date);
        timeTextView = findViewById(R.id.time);
        subjectTextView = findViewById(R.id.subject);
        adapterItems = new ArrayAdapter<String>(this,R.layout.subject_item,items);
        subjectTextView.setAdapter(adapterItems);
        create = findViewById(R.id.create_schedule);

        Intent intent = getIntent();
        uid=intent.getStringExtra("uid");

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(CreateLearningSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int displayedYear, int displayedMonth, int displayedDay) {
                        dateTextView.setText(displayedDay +"/" + (displayedMonth+1) + "/" + displayedYear);
                        year = displayedYear;
                        month = displayedMonth;
                        day = displayedDay;
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

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "StudyToWorldReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("StudyToWorld", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createSchedule() {
        //String subject, date,time;
        //subject = subjectTextView.getText().toString();
        //date = dateTextView.getText().toString();
        //time = timeTextView .getText().toString();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        //Query checkUser = reference.orderByChild("password").equalTo(password);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //DatabaseReference newScheduleRef = reference.child(password).child("Schedules");
                    DatabaseReference newScheduleRef = reference.child(uid).child("Schedules");
                    //Query largestId = newScheduleRef.orderByChild("id").limitToLast(1);
                    newScheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                reference.child(uid).child("Schedules").child(Integer.toString(0)).
                                        setValue(new Schedule(0, subject, year, month, day, hour, minute));
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
        //notification
        setAlarm();

    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent (this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeCalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeTextView.setText(String.format(Locale.getDefault(),"%02d:%02d", hour,minute));

                timeCalendar = Calendar.getInstance();
                timeCalendar.set(Calendar.HOUR_OF_DAY,hour);
                timeCalendar.set(Calendar.MINUTE,minute);
                timeCalendar.set(Calendar.SECOND,0);
                timeCalendar.set(Calendar.MILLISECOND,0);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour , minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }
}