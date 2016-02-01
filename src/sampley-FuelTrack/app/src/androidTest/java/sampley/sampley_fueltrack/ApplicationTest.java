package sampley.sampley_fueltrack;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.Date;

import sampley.sampley_fueltrack.data.Entry;
import sampley.sampley_fueltrack.data.Fuel;
import sampley.sampley_fueltrack.data.Station;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testEntry() {
        Date now = new Date();
        Entry entry = new Entry(now, "Costco", "regular", 99.3f, 45.234f, 100248.3f);

        assertTrue(entry.getDate().equals(now));
        assertTrue(entry.getStation().toString().equals("Costco"));
        assertTrue(entry.getFuel().getGrade().equals("regular"));
        assertTrue(entry.getFuel().getUnitCost() == 99.3f);
        assertTrue(entry.getFuel().getAmount() == 45.234f);
        assertTrue(entry.getOdometer() == 100248.3f);

        Date now2 = new Date();
        Entry entry2 = new Entry(now, new Station("Esso"), new Fuel("extra", 88.3f, 12.234f), 100200.3f);

        assertTrue(entry2.getDate().equals(now));
        assertTrue(entry2.getStation().toString().equals("Esso"));
        assertTrue(entry2.getFuel().getGrade().equals("extra"));
        assertTrue(entry2.getFuel().getUnitCost() == 88.3f);
        assertTrue(entry2.getFuel().getAmount() == 12.234f);
        assertTrue(entry2.getOdometer() == 100200.3f);
    }
}