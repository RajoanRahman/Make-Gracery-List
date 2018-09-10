package com.example.rifat.grocerylistitem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.rifat.grocerylistitem.Data.DatabaseHandler;
import com.example.rifat.grocerylistitem.Model.Grocery;
import com.example.rifat.grocerylistitem.R;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    EditText enterItem,enterQuantity;
    Button saveItem;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        byPassActivity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialogue();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createPopUpDialogue(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        // We inflate our popUp xml file na view. So, our cardView item inside popUp xml are now converted in View class.
        View view=getLayoutInflater().inflate(R.layout.popup,null);
        enterItem=(EditText) findViewById(R.id.grocery_item_editText);
        enterQuantity=(EditText) findViewById(R.id.grocery_quantity_editText);
        saveItem=(Button) findViewById(R.id.save_buton);

        builder.setView(view);
        builder.create();
        builder.show();

        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save to DB
                // TODO: Goto next Screen
                if (!enterItem.getText().toString().isEmpty()
                        && !enterQuantity.getText().toString().isEmpty()) {
                    saveGroceryToDB(v);
                }

            }
        });

    }

    public void saveGroceryToDB(View v){

        Grocery grocery = new Grocery();

        String newGrocery = enterItem.getText().toString();
        String newGroceryQuantity = enterQuantity.getText().toString();

        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);

        //Save to DB
        db.addGrocery(grocery);

        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        // Log.d("Item Added ID:", String.valueOf(db.getGroceriesCount()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //start a new activity
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200); //  1 second.


    }

    public void byPassActivity() {
        //Checks if database is empty; if not, then we just
        //go to ListActivity and show all added items

        if (db.getGroceriesCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }

    }
}
