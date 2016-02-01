package sampley.sampley_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    AppData data;

    @Override
    protected void onStart() {
        super.onStart();
        Log.println(Log.DEBUG, "Activity", "MainActivity started");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load shared data for app
        data = AppData.newInstance(this);

        // Get the buttons for the main activity
        Button buttonAddEntry = (Button)findViewById(R.id.add_entry);
        Button buttonViewEntries = (Button)findViewById(R.id.view_entries);

        // Set the on click listeners
        // "Add Entry" goes to the AddEntryActivity
        // "View Entries" goes to the ViewEntriesActivity
        buttonAddEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onAddEntryClicked(view);
            }
        });

        buttonViewEntries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onViewEntriesClicked(view);
            }
        });

        Log.println(Log.DEBUG, "Activity", "MainActivity created");
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


    public void onAddEntryClicked(View v) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);

    }

    public void onViewEntriesClicked(View v) {
        Intent intent = new Intent(this, ViewEntriesActivity.class);
        startActivity(intent);
    }
}
