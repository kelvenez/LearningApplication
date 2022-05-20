package com.example.studytoworld.UserProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studytoworld.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class EditUserProfile extends AppCompatActivity {
    private EditText emailTextView;
    private EditText passwordTextView;
    private EditText userNameTextView;
    String email, password, userName, uid;

    ImageView profileImageView;
    Uri imageUri;
    StorageReference profileImageReference;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        userNameTextView = findViewById(R.id.userName);

        profileImageReference = FirebaseStorage.getInstance().getReference();
        profileImageView = findViewById(R.id.profile_image);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });

        //showAllData
        showAllUserData();
        showProfileImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageUri = data.getData();
                profileImageView.setImageURI(imageUri);
                uploadPicture();
            }
    }

    private void showProfileImage() {
        try {
            File localFile = File.createTempFile("tempFile",".jpg");
            profileImageReference.child("images/"+uid).getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            profileImageView.setImageBitmap(bitmap);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed to retrieve profile image", Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference imageRef = profileImageReference.child("images/" + uid);
        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(R.id.profile_image),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                        Log.d("TestingProfile","OK");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed To Upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        email=intent.getStringExtra("email");
        password=intent.getStringExtra("password");
        userName=intent.getStringExtra("userName");
        uid = intent.getStringExtra("uid");
        emailTextView.setText(email);
        passwordTextView.setText(password);
        userNameTextView.setText(userName);
    }

    public void update(View view){

        if(isUserNameChanged()|| isPasswordChanged()||isEmailChanged()){
            Toast.makeText(this,"Data has been updated.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra("userName", StaticUserInfo.getUserName());
            intent.putExtra("password", StaticUserInfo.getPassword());
            intent.putExtra("email", StaticUserInfo.getEmail());
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Data is same and cannot be updated.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isUserNameChanged() {
        if(!userName.equals(userNameTextView.getEditableText().toString())){
            reference.child(uid).child("userName").setValue(userNameTextView.getEditableText().toString());
            userName=userNameTextView.getEditableText().toString();
            StaticUserInfo.setUserName(userName);
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isPasswordChanged() {
        if(!password.equals(passwordTextView.getEditableText().toString())){
            Log.d("Password", password);
            reference.child(uid).child("passwd").setValue(passwordTextView.getEditableText().toString());
            password=passwordTextView.getEditableText().toString();
            StaticUserInfo.setPassword(password);
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!email.equals(emailTextView.getEditableText().toString())){
            reference.child(uid).child("email").setValue(emailTextView.getEditableText().toString());
            email=emailTextView.getEditableText().toString();
            StaticUserInfo.setEmail(email);
            return true;
        }
        else{
            return false;
        }
    }
}