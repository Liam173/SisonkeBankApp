package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    //Create a global variable of each variable that will be used
    EditText userFirstname;
    EditText userLastname;
    EditText userEmail;
    EditText userPassword;
    EditText userPhoneNum;
    String userGender = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DatabaseHelper db = new DatabaseHelper(Registration.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    //This makes use of intents to send the user back to the login page when the "Already have an account? Login Here" text is clicked
    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //All validation required as specified, in order to ensure that a valid email and password is used
    public boolean validateNames(){
        userFirstname = findViewById(R.id.editTextPersonName);
        userLastname = findViewById(R.id.editTextPersonSurname);
        if(userFirstname.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your first name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userLastname.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your last name", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public boolean validateEmail() {
        userEmail = findViewById(R.id.editTextEmailAddress);
        if (userEmail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter email address", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (userEmail.getText().toString().trim().matches(emailPattern)) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public boolean validatePassword(){
        userPassword = findViewById(R.id.editTextPassword);
        if(userPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if(userPassword.getText().toString().length()>4){
                return true;
            } else {
                Toast.makeText(getApplicationContext(),"Password is too short", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public boolean validateMobile(){
        userPhoneNum = findViewById(R.id.editTextPhone);
        if(userPhoneNum.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public boolean validateGender(){
        if(userGender == ""){
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void createUserAccount(){
        UserDetails userDetails;
        //If the user account already exists based on the email they used, then a toast message will be shown
        if(userEmail.getText().toString().equals(db.findEmail(db.getReadableDatabase(), userEmail.getText().toString()))){
            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
        }else{//Else an account will be created for the user with the help of the addUser method found inside the DatabaseHelper class
            try{
                userDetails = new UserDetails(-1, userFirstname.getText()+"", userLastname.getText()+"", userEmail.getText()+"", userPassword.getText()+"", userPhoneNum.getText()+"", userGender, 5000, 2000);
            }catch(Exception e){
                userDetails = new UserDetails(-1, "error", "error", "error", "error", "error", "error", 0, 0);
                Toast.makeText(Registration.this, "Error creating the customer", Toast.LENGTH_SHORT).show();
            }
            DatabaseHelper dataBaseHelper = new DatabaseHelper(Registration.this);
            boolean success = dataBaseHelper.addUser(userDetails);
            Toast.makeText(getApplicationContext(), "The user account creation was successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    //This method is used to obtain the gender selected from the radio buttons
    public void onRadioButtonClicked(View view){
        RadioGroup rg= findViewById(R.id.radioGroup);
        userGender = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
    }

    //Only call the createUserAccount method if all validation was successful
    public void validateAllFields(View v){
        if (validateNames() == true && validateEmail() == true && validatePassword() == true && validateMobile() == true && validateGender() == true){
            createUserAccount();
        }
    }
}

