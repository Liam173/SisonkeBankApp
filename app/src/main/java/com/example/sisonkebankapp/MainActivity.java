package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Create a global variable of each variable that will be used
    Button myButton;
    int id;
    EditText myEmail;
    EditText myPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DatabaseHelper myDatabaseHelper = new DatabaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //All validation required as specified, in order to ensure that a valid email and password is used
    //Based on the validation/if statements' conditions the value of true or false will be returned.
    // If the value is false then the validation failed and the user will not log in
    public boolean validateAll(){
        myPassword = findViewById(R.id.textInputPassword);
        myEmail = findViewById(R.id.textInputEmail);
        myButton = findViewById(R.id.buttonLogin);
        if(myEmail.getText().toString().isEmpty() || myPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please enter email address and password",Toast.LENGTH_SHORT).show();
            myPassword.setText("");
            myEmail.setText("");
            return false;
        }else {
            if (myEmail.getText().toString().trim().matches(emailPattern)) {
                if(myPassword.getText().toString().length() > 4){

                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    myPassword.setText("");
                    return false;
                }
            } else {
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                myEmail.setText("");
                return false;
            }
        }
    }

    //This unlike the above method validates the password and username against the actual database to ensure this user actually exists
    public boolean checkPasswordAndUsernameAgainstDatabase(){
        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();
        String dbEmail = myDatabaseHelper.findEmail(myDatabaseHelper.getReadableDatabase(),email);
        String dbPassword = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(),email, "password");
        if(dbEmail.equals(email) && dbPassword.equals(password)){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Password does not fit the username supplied", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //This makes use of intents to open the registration page when the Register Here text is clicked on
    public void openRegistration(View v){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    //This opens the Home page if all the validation passes, this also sends through the email of the user signing in
    //This email will be used to identify the user and display his/her name in the Home Page
    public void openHome(View v) {
        if(validateAll() == true & checkPasswordAndUsernameAgainstDatabase() == true){
            Intent i = new Intent(this, Home.class);
            i.putExtra("email", myDatabaseHelper.findEmail(myDatabaseHelper.getReadableDatabase(), myEmail.getText().toString()));
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
        }
    }
}