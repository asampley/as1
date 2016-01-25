package sampley.sampley_fueltrack;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import sampley.sampley_fueltrack.data.Entry;

/**
 * Created by A on 2016-01-24.
 */
public class AppData {
    private static AppData instance;
    private Context context;
    private List<Entry> entries;
    private static final String ENTRY_FILE_NAME = "entries.sav";

    // prevent use of default constructor, other than to create one instance
    protected AppData(Context c) {
        this.entries = new ArrayList<Entry>();
        this.context = c;
        try {
            loadEntries();
        } catch (FileNotFoundException e) {
            Log.println(Log.ERROR, "AppData", "Unable to load entries from: " + ENTRY_FILE_NAME + " (File not found)");
        } catch (ClassNotFoundException e) {
            Log.println(Log.ERROR, "AppData", "Unable to load entries from: " + ENTRY_FILE_NAME + " (Class not found)");
        } catch (IOException e) {
            Log.println(Log.ERROR, "AppData", "Unable to load entries from: " + ENTRY_FILE_NAME + " (IOException)");
        }
    }

    public static AppData newInstance(Context c) {
        if (instance == null) {
            return new AppData(c);
        } else {
            return instance;
        }
    }


    public static void writeToLocalFile(Context context, String localFilePath, Object o) throws IOException {
        ObjectOutputStream oout = new ObjectOutputStream(context.openFileOutput(localFilePath, Context.MODE_PRIVATE));
        oout.writeObject(o);
        oout.close();
    }

    public static Object readFromLocalFile(Context context, String localFilePath) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = new ObjectInputStream(context.openFileInput(localFilePath));
        Object o = oin.readObject();
        oin.close();
        return o;
    }

    public void loadEntries() throws IOException, ClassNotFoundException {
        entries = (List<Entry>)readFromLocalFile(context, ENTRY_FILE_NAME);
    }

    public void saveEntries() throws IOException {
        writeToLocalFile(context, ENTRY_FILE_NAME, entries);
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
