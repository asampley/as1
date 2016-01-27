package sampley.sampley_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sampley.sampley_fueltrack.data.Entry;

public class ViewEntriesActivity extends AppCompatActivity {
    String entriesFileName = "entries.sav";
    List entries;
    ListAdapter entriesAdapter;
    ListView entriesView;

    AppData data;

    @Override
    protected void onStart() {
        super.onStart();
        Log.println(Log.DEBUG, "ViewEntriesActivity", "ViewEntriesActivity started");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = AppData.newInstance(this);

        entries = data.getEntries();

        entriesAdapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1, entries);

        entriesView = (ListView)findViewById(R.id.entries_list);
        entriesView.setAdapter(entriesAdapter);
        entriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView aView, View view, int position, long index) {
                editActivity();
            }
        });

        Log.println(Log.DEBUG, "ViewEntriesActivity", "ViewEntriesActivity created");

        Log.println(Log.DEBUG, "ViewEntriesActivity", entries.size() + " entries loaded");
    }

    public void editActivity() {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }
}
