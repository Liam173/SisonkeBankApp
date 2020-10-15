package com.example.sisonkebankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Transfer extends AppCompatActivity {
    //Create a global variable of each variable that will be used
    String currentBalance;
    String savingsBalance;
    String email;
    int id;
    EditText valueEntered;
    DatabaseHelper myDatabaseHelper = new DatabaseHelper(Transfer.this);
    String[] arraySpinner = new String[] {"Current to Savings", "Savings to Current"};
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        //This is required to create the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //This pulls the id field from the Home page with the help of intents
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt("id");
        }
        //Calls the remaining methods
        populateSpinner();
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

    public void populateSpinner(){
        s = (Spinner) findViewById(R.id.mySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void setCurrentBalance(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            email = extras.getString("email");
        }
        currentBalance = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "currentBalance");
        TextView currentBalanceDisplay = findViewById(R.id.CurrentAccountBalance);
        currentBalanceDisplay.setText("R " + currentBalance + ".00");
    }

    public void setSavingsBalance(){
        savingsBalance = myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "savingsBalance");
        TextView savingsBalanceDisplay = findViewById(R.id.SavingsAccountBalance);
        savingsBalanceDisplay.setText("R " + savingsBalance + ".00");
    }

    public void transferFunds(View view){
        //Create a string value to store the value entered that must be transferred
        valueEntered = findViewById(R.id.editTextTextPersonName3);
        //Create an if statement that ensures that there is a value entered in the transfer amount text field
        if(valueEntered.getText().toString().equals("")){
            //If the value is empty then toast that a value must be entered
            Toast.makeText(getApplicationContext(),"Value must be entered", Toast.LENGTH_SHORT).show();
        }else{
            //Convert the value to an integer
            int value = Integer.parseInt(valueEntered.getText().toString());
            //Check if the spinner/combo box's value is "Current to Savings"
            if(s.getSelectedItem().equals("Current to Savings")){ // Spinner = Current to Savings
                //Check that the value given by the user does not exceed the value that is available in the Current account
                if(Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "currentBalance")) >= value){
                    makeTransferFromCurrent(value);
                }else{
                    Toast.makeText(getApplicationContext(),"Insufficient funds", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Check that the value given by the user does not exceed the value that is available in the Savings account
                if(Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(), email, "savingsBalance")) >= value){ // Spinner = Savings to Current
                    makeTransferFromSavings(value);
                }else{
                    Toast.makeText(getApplicationContext(),"Insufficient funds", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //Recall both methods to essentially refresh the two textfields holding the current and savings amounts
        setCurrentBalance();
        setSavingsBalance();
    }

    //Reduce the current value by the amount entered
    public int getNewCurrentValueMinus(int value){
        int currentVal = Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(),email, "currentBalance"));
        int newValue = currentVal-value;
        return newValue;
    }

    //Increase the current value by the amount entered
    public int getNewCurrentValueAdd(int value){
        int currentVal = Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(),email, "currentBalance"));
        int newValue = currentVal+value;
        return newValue;
    }

    //Reduce the savings value by the amount entered
    public int getNewSavingsValueMinus(int value){
        int currentVal = Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(),email, "savingsBalance"));
        int newValue = currentVal-value;
        return newValue;
    }

    //Increase the savings value by the amount entered
    public int getNewSavingsValueAdd(int value){
        int currentVal = Integer.parseInt(myDatabaseHelper.getUserDetails(myDatabaseHelper.getReadableDatabase(),email, "savingsBalance"));
        int newValue = currentVal+value;
        return newValue;
    }

    //Call the updateBalance method found in the DatabaseHelper, this will be the method used when the spinner is set to Current to Savings
    public void makeTransferFromCurrent(int value){
        Toast.makeText(getApplicationContext(),"id: " + id + " current: " + getNewCurrentValueMinus(value) + " savings: " + getNewSavingsValueAdd(value), Toast.LENGTH_SHORT).show();
        myDatabaseHelper.updateBalance(myDatabaseHelper.getWritableDatabase(),id,getNewCurrentValueMinus(value),getNewSavingsValueAdd(value));
        Toast.makeText(getApplicationContext(),"Transfer completed successfully", Toast.LENGTH_SHORT).show();
    }

    //Call the updateBalance method found in the DatabaseHelper, this will be the method used when the spinner is set to Savings to Current
    public void makeTransferFromSavings(int value){
        Toast.makeText(getApplicationContext(),"id: " + id + " current: " + getNewCurrentValueMinus(value) + " savings: " + getNewSavingsValueAdd(value), Toast.LENGTH_SHORT).show();
        myDatabaseHelper.updateBalance(myDatabaseHelper.getWritableDatabase(),id,getNewCurrentValueAdd(value),getNewSavingsValueMinus(value));
        Toast.makeText(getApplicationContext(),"Transfer completed successfully", Toast.LENGTH_SHORT).show();
    }
}
