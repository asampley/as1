package sampley.sampley_fueltrack.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Every entry contains information about when the car was last refuelled. Each entry contains
 * the following information:
 *
 * <p>
 * A {@link Date} which is when the refuelling was done. <br>
 * A {@link Station} which is where the refuelling was done. <br>
 * An odometer reading, in km, read off the car when the refuelling was done <br>
 * A {@link Fuel} object, which knows the grade, amount, unit cost, and total cost of the fuel. <br>
 * </p>
 *
 * <p>
 * {@link Entry} places no restriction on editing the data inside.
 * </p>
 */
public class Entry implements Serializable {
    private Date date;
    private Station station;
    private Fuel fuel;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Create a new {@link Entry}, supplying all the necessary information.
     * @param date The date of refuelling.
     * @param station The station at which refuelling occurred.
     * @param fuel All information about the fuel purchased.
     * @see Date
     * @see Station
     * @see Fuel
     */
    public Entry(Date date, Station station, Fuel fuel) {
        this.date = date;
        this.station = station;
        this.fuel = fuel;
    }

    /**
     * Create a new {@link Entry}, supplying the station as a name only.
     * @param date The date of refuelling.
     * @param station The name of the station.
     * @param fuel All information about the fuel purchased.
     * @see Date
     * @see Fuel
     */
    public Entry(Date date, String station, Fuel fuel) {
        this(date, new Station(station), fuel);
    }

    /**
     * Create a new {@link Entry}, supplying the fuel information directly.
     * @param date The date of refuelling.
     * @param station The station at which the refuelling occurred.
     * @param fuelGrade The grade of the fuel. (Such as "Regular")
     * @param fuelUnitCost The unit cost of fuel, in cents per L.
     * @param fuelAmount The amount of fuel purchased, in L.
     */
    public Entry(Date date, Station station, String fuelGrade, float fuelUnitCost, float fuelAmount) {
        this(date, station, new Fuel(fuelGrade, fuelUnitCost, fuelAmount));
    }

    /**
     * Create a new {@link Entry}, supplying the fuel information directly.
     * @param date The date of refuelling.
     * @param station The name of the station.
     * @param fuelGrade The grade of the fuel. (Such as "Regular")
     * @param fuelUnitCost The unit cost of fuel, in cents per L.
     * @param fuelAmount The amount of fuel purchased, in L.
     */
    public Entry(Date date, String station, String fuelGrade, float fuelUnitCost, float fuelAmount) {
        this(date, new Station(station), new Fuel(fuelGrade, fuelUnitCost, fuelAmount));
    }


    @Override
    public String toString() {
        return dateFormat.format(date) + " " + station + " " + fuel;
    }
}
