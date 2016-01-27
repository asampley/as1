package sampley.sampley_fueltrack;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

        // add button listeners
        Button buttonSaveEntry = (Button)findViewById(R.id.button_save_entry);
        Button buttonCancelEntry = (Button)findViewById(R.id.button_cancel_entry);
        buttonSaveEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveEntry();
            }
        });
        buttonCancelEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        // auto-fill date field
        EditText dateView = (EditText)findViewById(R.id.text_enter_date);
        dateView.setText(dateFormat.format(new Date()));

        // make date format automatically insert dashes after year and month
        dateView.addTextChangedListener(new DateTextWatcher());
    }

    public void saveEntry() {
        EditText dateView = (EditText)findViewById(R.id.text_enter_date);
        EditText stationView = (EditText)findViewById(R.id.text_enter_station);
        EditText amountView = (EditText)findViewById(R.id.text_enter_amount);
        EditText gradeView = (EditText)findViewById(R.id.text_enter_grade);
        EditText costView = (EditText)findViewById(R.id.text_enter_cost);
        EditText odometerView = (EditText)findViewById(R.id.text_enter_odometer);

        // check if the data is valid, and if not, build an alert and pop it up.
        boolean validData = true;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        Date date = null;
        Station station = null;
        float amount = 0;
        float cost = 0;
        float odometer = 0;

        try {
            date = dateFormat.parse(dateView.getText().toString());
        } catch (ParseException e) {
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Date: " + e.getMessage());
            validData = false;
            alertBuilder.setMessage("Date invalid. Format is:\nYYYY-MM-DD");
            //alertBuilder.setNeutralButton("OK", null);
            alertBuilder.create();
        }

        String stationName = stationView.getText().toString();
        if (!stationName.trim().equals("")) {
            station = new Station(stationView.getText().toString());
        } else {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Name: " + stationName);
        }
        String grade = gradeView.getText().toString();
        if (grade.trim().equals("")) {
            validData = false;
        }

        try {
            amount = Float.parseFloat(amountView.getText().toString());
        } catch (NumberFormatException e) {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Volume: " + e.getMessage());
        }

        try {
            cost = Float.parseFloat(costView.getText().toString());
        } catch (NumberFormatException e) {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Cost: " + e.getMessage());
        }

        try {
            odometer = Float.parseFloat(odometerView.getText().toString());
        } catch (NumberFormatException e) {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Odometer Reading: " + e.getMessage());
        }


        if (validData) {
            Fuel fuel = new Fuel(grade, cost, amount);
            Entry entry = new Entry(date, station, fuel, odometer);
            entries.add(entry);
            Log.println(Log.INFO, "AddEntryActivity", "Added Entry: " + entry.toString());
        }


            try {
                data.saveEntries();
            } catch (IOException e) {
                Log.println(Log.ERROR, "AddEntryActivity", "Unable to save entry to file");
            }

    }
}
