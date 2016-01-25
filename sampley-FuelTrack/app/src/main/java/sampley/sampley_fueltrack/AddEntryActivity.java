package sampley.sampley_fueltrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sampley.sampley_fueltrack.data.Entry;
import sampley.sampley_fueltrack.data.Fuel;
import sampley.sampley_fueltrack.data.Station;

public class AddEntryActivity extends AppCompatActivity {
    private AppData data;
    private List<Entry> entries;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = AppData.newInstance(this);
        entries = data.getEntries();

        Button buttonSaveEntry = (Button)findViewById(R.id.button_save_entry);
        buttonSaveEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveEntry();
            }
        });

        EditText dateView = (EditText)findViewById(R.id.text_enter_date);
        dateView.setText(dateFormat.format(new Date()));
    }

    public void saveEntry() {
        EditText dateView = (EditText)findViewById(R.id.text_enter_date);
        EditText stationView = (EditText)findViewById(R.id.text_enter_station);
        EditText amountView = (EditText)findViewById(R.id.text_enter_amount);
        EditText gradeView = (EditText)findViewById(R.id.text_enter_grade);
        EditText costView = (EditText)findViewById(R.id.text_enter_cost);

        try {
            Date date = dateFormat.parse(dateView.getText().toString());
            Station station = new Station(stationView.getText().toString());
            float amount = Float.parseFloat(amountView.getText().toString());
            String grade = gradeView.getText().toString();
            float cost = Float.parseFloat(costView.getText().toString());
            Fuel fuel = new Fuel(grade, cost, amount);

            Entry entry = new Entry(date, station, fuel);

            entries.add(entry);
            Log.println(Log.INFO, "AddEntryActivity", "Added Entry: " + entry.toString());

            try {
                data.saveEntries();
            } catch (IOException e) {
                Log.println(Log.ERROR, "AddEntryActivity", "Unable to save entry to file");
            }

        } catch (ParseException | NumberFormatException e) {
            if (e.getMessage() != null) {
                Log.println(Log.ERROR, "AddEntryActivity", "Invalid arguments: " + e.getMessage());
            } else {
                Log.println(Log.ERROR, "AddEntryActivity", "Invalid arguments. No reason given.");
            }
        }
    }
}
