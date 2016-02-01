package sampley.sampley_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import sampley.sampley_fueltrack.MVC.MVCView;
import sampley.sampley_fueltrack.data.Entry;

public class ViewEntriesActivity extends AppCompatActivity implements MVCView<AppData> {
    private String entriesFileName = "entries.sav";
    private List entries;
    private ArrayAdapter entriesAdapter;
    private ListView entriesView;

    private AppData data;

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

        // add self to get notified of changes to data
        data.addView(this);
        // TODO do not get properly notified yet.

        // get the data to display
        entries = data.getEntries();
        entriesAdapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1, entries);
        entriesView = (ListView)findViewById(R.id.entries_list);
        entriesView.setAdapter(entriesAdapter);
        entriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView aView, View view, int position, long index) {
                launchEditActivity((int) index);
            }
        });

        Log.println(Log.DEBUG, "ViewEntriesActivity", "ViewEntriesActivity created");

        Log.println(Log.DEBUG, "ViewEntriesActivity", entries.size() + " entries loaded");
    }

    public void launchEditActivity(int index) {
        // Create Activity, but notify it to edit an existing entry
        Intent intent = new Intent(this, AddEntryActivity.class);
        intent.putExtra(AddEntryActivity.INTENT_MODE_EDIT, index);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        data.removeView(this);
        super.onDestroy();
    }

    @Override
    public void update(AppData m) {
        entriesAdapter.notifyDataSetChanged();
    }
}
