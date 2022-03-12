package com.example.studytoworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ImageButton table1 ,newPopUp_cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table1 = findViewById(R.id.table1);
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTableDiaglog();
            }
        });
    }

    private void createNewTableDiaglog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View tablePopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        newPopUp_cancel = (ImageButton) tablePopUpView.findViewById(R.id.cancelButton);
        dialogBuilder.setView(tablePopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        newPopUp_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define the cancel function
                dialog.dismiss();
            }
        });

    }
}

