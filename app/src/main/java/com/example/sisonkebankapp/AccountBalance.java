package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AccountBalance extends AppCompatActivity {
    //Create a global variable of each variable that will be used
    String currentBalance;
    String savingsBalance;
    String firstname;
    String lastname;
    String email;
    DatabaseHelper myDatabaseHelper = new DatabaseHelper(AccountBalance.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);
        //This is required to create the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //This pulls the email field from the Home page with the help of intents
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
        }
        //Calls the remaining methods
        setFirstname();
        setLastname();
        setCurrentBalance();
        setSavingsBalance();
    }

    //Used to ensure that after using the back button the user is still able to access
    // the AccountBalance page without the system crashing, makes the back button work like the android back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Calls the getUserDetails method found in the DatabaseHelper class, to pull the first name of the user at the given email's position
    public void setFirstname(){
        firstname = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "firstName");
        TextView firstnameDisplay = findViewById(R.id.AccountHolderName);
        firstnameDisplay.setText(firstname);
    }

    //Calls the getUserDetails method found in the DatabaseHelper class, to pull the surname of the user at the given email's position
    public void setLastname(){
        lastname = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "lastName");
        TextView lastnameDisplay = findViewById(R.id.AccountHolderSurname);
        lastnameDisplay.setText(lastname);
    }

    //Calls the getUserDetails method found in the DatabaseHelper class, to pull the current account balance of the user at the given email's position
    public void setCurrentBalance(){
        currentBalance = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "currentBalance");
        TextView currentBalanceDisplay = findViewById(R.id.CurrentAccountBalance);
        currentBalanceDisplay.setText("R " + currentBalance + ".00");
    }

    //Calls the getUserDetails method found in the DatabaseHelper class, to pull the savings account balance of the user at the given email's position
    public void setSavingsBalance(){
        savingsBalance = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "savingsBalance");
        TextView savingsBalanceDisplay = findViewById(R.id.SavingsAccountBalance);
        savingsBalanceDisplay.setText("R " + savingsBalance + ".00");
    }
}