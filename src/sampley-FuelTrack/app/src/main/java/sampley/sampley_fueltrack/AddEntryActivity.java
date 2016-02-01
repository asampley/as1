package sampley.sampley_fueltrack;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private AlertDialog.Builder alertBuilder;

    private static final String INVALID_DATE_MESSAGE = "Date invalid.\nFormat is:YYYY-MM-DD";
    private static final String INVALID_ODOMETER_MESSAGE = "Odometer reading invalid.\nShould be a number.";
    private static final String INVALID_STATION_MESSAGE = "Station name invalid.\nShould be non-empty name.";
    private static final String INVALID_GRADE_MESSAGE = "Fuel grade invalid.\nShould be non-empty name.";
    private static final String INVALID_VOLUME_MESSAGE = "Volume invalid.\nShould be a number.";
    private static final String INVALID_COST_MESSAGE = "Cost per Litre invalid.\nShould be a number.";

    public static final String INTENT_MODE_EDIT = "MODE_EDIT";
    Integer editIndex;

    EditText dateView;
    EditText stationView;
    EditText gradeView;
    EditText amountView;
    EditText costView;
    EditText odometerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get identifiers for text input text fields
        dateView = (EditText)findViewById(R.id.text_enter_date);
        stationView = (EditText)findViewById(R.id.text_enter_station);
        amountView = (EditText)findViewById(R.id.text_enter_amount);
        gradeView = (EditText)findViewById(R.id.text_enter_grade);
        costView = (EditText)findViewById(R.id.text_enter_cost);
        odometerView = (EditText)findViewById(R.id.text_enter_odometer);

        data = AppData.newInstance(this);
        entries = data.getEntries();

        // check if we are editting a current entry
        editIndex = getIntent().getIntExtra(this.INTENT_MODE_EDIT, -1);
        if (editIndex < 0) {
            editIndex = null;
        }

        // initialize fields
        if (editIndex == null) {
            // set the date to the current date
            dateView.setText(dateFormat.format(new Date()));
        } else {
            Entry entry = entries.get(editIndex);
            dateView.setText(dateFormat.format(entry.getDate()));
            stationView.setText(entry.getStation().toString());
            gradeView.setText(entry.getFuel().getGrade());
            amountView.setText(Float.toString(entry.getFuel().getAmount()));
            costView.setText(Float.toString(entry.getFuel().getUnitCost()));
            odometerView.setText(Float.toString(entry.getOdometer()));
        }

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

        // make date format automatically insert dashes after year and month
        dateView.addTextChangedListener(new DateTextWatcher());

        // create builder for AlertDialogs, for when the user makes incorrect input.
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setNeutralButton("OK", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void saveEntry() {
        // check if the data is valid, and if not, build an alert and pop it up.
        boolean validData = true;

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
            alertBuilder.setMessage(INVALID_DATE_MESSAGE);
            //alertBuilder.setNeutralButton("OK", null);
            alertBuilder.create().show();
        }

        try {
            odometer = Float.parseFloat(odometerView.getText().toString());
        } catch (NumberFormatException e) {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Odometer Reading: " + e.getMessage());
            alertBuilder.setMessage(INVALID_ODOMETER_MESSAGE);
        }

        String stationName = stationView.getText().toString();
        if (!stationName.trim().equals("")) {
            station = new Station(stationView.getText().toString());
        } else {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Name: " + stationName);
            alertBuilder.setMessage(INVALID_STATION_MESSAGE);
        }

        String grade = gradeView.getText().toString();
        if (grade.trim().equals("")) {
            validData = false;
            alertBuilder.setMessage(INVALID_GRADE_MESSAGE);
        }

        try {
            amount = Float.parseFloat(amountView.getText().toString());
        } catch (NumberFormatException e) {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Volume: " + e.getMessage());
            alertBuilder.setMessage(INVALID_VOLUME_MESSAGE);
        }

        try {
            cost = Float.parseFloat(costView.getText().toString());
        } catch (NumberFormatException e) {
            validData = false;
            Log.println(Log.ERROR, "AddEntryActivity", "Invalid Cost: " + e.getMessage());
            alertBuilder.setMessage(INVALID_COST_MESSAGE);
        }


        if (validData) {
            Fuel fuel = new Fuel(grade, cost, amount);

            if (editIndex == null) { // do not edit
                Entry entry = new Entry(date, station, fuel, odometer);
                entries.add(entry);
                Log.println(Log.INFO, "AddEntryActivity", "Added Entry: " + entry.toString());
            } else { // edit existing entry
                Entry editEntry = entries.get(editIndex);
                editEntry.setDate(date);
                editEntry.setFuel(fuel);
                editEntry.setStation(station);
                editEntry.setOdometer(odometer);
                Log.println(Log.INFO, "AddEntryActivity", "Editted Entry (" + editIndex + "): " + editEntry.toString());
            }

            // update views, now that all data manipulation is done
            data.notifyViews();
            finish();
        } else {
            alertBuilder.create().show();
        }


        try {
            data.saveEntries();
        } catch (IOException e) {
            Log.println(Log.ERROR, "AddEntryActivity", "Unable to save entry to file");
        }
    }
}
