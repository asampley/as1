package sampley.sampley_fueltrack;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import sampley.sampley_fueltrack.MVC.MVCModel;
import sampley.sampley_fueltrack.MVC.MVCView;
import sampley.sampley_fueltrack.data.Entry;

/**
 * This class stores all data used throughout the app, using a singleton model: Only one of these
 * objects ever exists.
 *
 * <p>
 * The {@link AppData} stores all the entries, and manages loading and saving to a file. It also
 * contains the context under which it was created, which is used to manage file access.
 * </p>
 */
public class AppData extends MVCModel {
    private static AppData instance;
    private Context context;
    private List<Entry> entries;
    public static final String ENTRY_FILE_NAME = "entries.sav";

    // prevent use of default constructor, other than to create one instance
    protected AppData(Context c) {
        super();
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
            instance = new AppData(c);
        } else {
            instance.context = c;
        }
        return instance;
    }


    protected static void writeToLocalFile(Context context, String localFilePath, Object o) throws IOException {
        ObjectOutputStream oout = new ObjectOutputStream(context.openFileOutput(localFilePath, Context.MODE_PRIVATE));
        oout.writeObject(o);
        oout.close();
    }

    protected static Object readFromLocalFile(Context context, String localFilePath) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = new ObjectInputStream(context.openFileInput(localFilePath));
        Object o = oin.readObject();
        oin.close();
        return o;
    }

    /**
     * Load all the entries saved on the device. The file location is determined by the {@link Context}
     * which created this instance of {@link AppData}. The file name is stored as a constant
     * (ENTRY_FILE_NAME) in the class definition.
     * @throws IOException There was an error reading the file.
     * @throws ClassNotFoundException The file contains an unknown class.
     */
    public void loadEntries() throws IOException, ClassNotFoundException {
        entries = (List<Entry>)readFromLocalFile(context, ENTRY_FILE_NAME);
    }

    /**
     * Save all the entries to the device. The file location is determined by the {@link Context}
     * which created this instance of {@link AppData}. The file name is stored as a constant
     * (ENTRY_FILE_NAME) in the class definition.
     * @throws IOException There was an error writing the file.
     */
    public void saveEntries() throws IOException {
        writeToLocalFile(context, ENTRY_FILE_NAME, entries);
    }

    /**
     * Get the list of entries.
     * @return The list of entries.
     */
    public List<Entry> getEntries() {
        return entries;
    }
}
