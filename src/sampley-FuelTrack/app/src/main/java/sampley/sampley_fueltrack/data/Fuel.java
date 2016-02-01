package sampley.sampley_fueltrack.data;

import java.io.Serializable;

/**
 * A {@link Fuel} object contains information for the fuel purchased in each {@link Entry}.
 *
 * <p>
 * A {@link String} grade of the fuel, such as "Regular", or "Unleaded".<br>
 * A unit cost of the fuel, in cents per L.<br>
 * An amount of fuel purchased, in L.
 * </p>
 *
 * <p>
 * From these attributes, the total cost of the fuel is also computed, and can be queried from the
 * object.
 * </p>
 * @see Entry
 */
public class Fuel implements Serializable {
    private String grade;
    private float unitCost;
    private float amount;

    /**
     * Create a {@link Fuel} object, specifying all necessary attributes.
     * @param grade The fuel grade, such as "Regular".
     * @param unitCost The unit cost, in cents per L.
     * @param amount The amount of fuel, in L.
     */
    public Fuel(String grade, float unitCost, float amount) {
        this.grade = grade;
        this.unitCost = unitCost;
        this.amount = amount;
    }

    /**
     * Get the total cost for the fuel.
     * @return The total cost, in dollars.
     */
    public float getTotalCost() {
        return unitCost * amount / 100;
    }

    @Override
    public String toString() {
        return String.format("%s %.3fL@%.1f¢/L ($%.2f)", grade, amount, unitCost, getTotalCost());
    }

    public float getAmount() {
        return amount;
    }

    public String getGrade() {
        return grade;
    }

    public float getUnitCost() {
        return unitCost;
    }
}
