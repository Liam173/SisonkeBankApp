package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Home extends AppCompatActivity {
    //Create a global variable of each variable that will be used
    int id = 0;
    String firstName = "";
    private String lastName = "";
    String email = "";
    private String password = "";
    private String phoneNumber = "";
    private String gender = "";
    private String pulledEmail = "";
    DatabaseHelper myDatabaseHelper = new DatabaseHelper(Home.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Call the only remaining method
        storeAllComponents();
    }

    //This makes use of intents to open the login page when the logout button is pressed
    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //This makes use of intents to open the AccountsBalance page when the View Account Balance button is pressed
    //Email is also sent through from Home page to AccountsBalance page
    public void openAccountBalance(View view) {
        Intent intent = new Intent(this, AccountBalance.class);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
        }
        intent.putExtra("email", email);
        startActivity(intent);
    }

    //This makes use of intents to open the Transfer page when the Transfer Between Accounts button is pressed
    //Email is also sent through from Home page to Transfer page
    public void openTransfer(View view) {
        Intent intent = new Intent(this, Transfer.class);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
        }
        intent.putExtra("email", email);
        intent.putExtra("id", Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "id")));
        startActivity(intent);
    }

    //This method is used to obtain the user's first name from the Login page
    public void storeAllComponents(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
        }
        firstName = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "firstName");
        TextView usersName = findViewById(R.id.tfUsersName);
        usersName.setText(firstName);
    }
}