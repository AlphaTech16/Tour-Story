package com.avash.tourstory.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avash.tourstory.R;
import com.avash.tourstory.database.UserManager;
import com.avash.tourstory.model.UserModel;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText nameET, emailET, mobileET, addressET;
    Button updateBtn;

    UserManager userManager;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        nameET = (EditText) findViewById(R.id.fullNameEditText);
        emailET = (EditText) findViewById(R.id.emailEditText);
        mobileET = (EditText) findViewById(R.id.mobileEditText);
        addressET = (EditText) findViewById(R.id.addressEditText);

        updateBtn = (Button) findViewById(R.id.updateProfileButton);

        userManager = new UserManager(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        int uid = sharedPreferences.getInt("uid",0);

        userModel = userManager.getUserProfile(uid);

        nameET.setText(userModel.getFullName());
        mobileET.setText(userModel.getMobile());
        emailET.setText(userModel.getEmail());
        addressET.setText(userModel.getAddress());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,mobile,email,address;
                name = nameET.getText().toString();
                mobile = mobileET.getText().toString();
                email = emailET.getText().toString();
                address = addressET.getText().toString();

                userModel = new UserModel(name,email,mobile,address);
                long result = userManager.updateUserProfile(userModel);

                if (result>0){
                    Toast.makeText(UpdateProfileActivity.this, "Information Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
