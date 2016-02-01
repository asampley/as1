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
    private float odometer;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Create a new {@link Entry}, supplying all the necessary information.
     * @param date The date of refuelling.
     * @param station The station at which refuelling occurred.
     * @param fuel All information about the fuel purchased.
     * @param odometer Reading from the odometer, in km.
     * @see Date
     * @see Station
     * @see Fuel
     */
    public Entry(Date date, Station station, Fuel fuel, float odometer) {
        this.date = date;
        this.station = station;
        this.fuel = fuel;
        this.odometer = odometer;
    }

    /**
     * Create a new {@link Entry}, supplying the station as a name only.
     * @param date The date of refuelling.
     * @param station The name of the station.
     * @param fuel All information about the fuel purchased.
     * @param odometer Reading from the odometer, in km.
     * @see Date
     * @see Fuel
     */
    public Entry(Date date, String station, Fuel fuel, float odometer) {
        this(date, new Station(station), fuel, odometer);
    }

    /**
     * Create a new {@link Entry}, supplying the fuel information directly.
     * @param date The date of refuelling.
     * @param station The station at which the refuelling occurred.
     * @param fuelGrade The grade of the fuel. (Such as "Regular")
     * @param fuelUnitCost The unit cost of fuel, in cents per L.
     * @param fuelAmount The amount of fuel purchased, in L.
     * @param odometer Reading from the odometer, in km.
     */
    public Entry(Date date, Station station, String fuelGrade, float fuelUnitCost, float fuelAmount, float odometer) {
        this(date, station, new Fuel(fuelGrade, fuelUnitCost, fuelAmount), odometer);
    }

    /**
     * Create a new {@link Entry}, supplying the fuel information directly.
     * @param date The date of refuelling.
     * @param station The name of the station.
     * @param fuelGrade The grade of the fuel. (Such as "Regular")
     * @param fuelUnitCost The unit cost of fuel, in cents per L.
     * @param fuelAmount The amount of fuel purchased, in L.
     * @param odometer Reading from the odometer, in km.
     */
    public Entry(Date date, String station, String fuelGrade, float fuelUnitCost, float fuelAmount, float odometer) {
        this(date, new Station(station), new Fuel(fuelGrade, fuelUnitCost, fuelAmount), odometer);
    }


    @Override
    public String toString() {
        return dateFormat.format(date) + " " + station + " " + fuel;
    }

    public Date getDate() {
        return date;
    }

    public Station getStation() {
        return station;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }
}
