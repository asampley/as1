package sampley.sampley_fueltrack.data;

import java.io.Serializable;

/**
 * A {@link Station} object contains a name, and is meant to be the station that was used for
 * refuelling in an {@link Entry}.
 */
public class Station implements Serializable {
    private String name;

    /**
     * Create a new station, with the specified name.
     * @param name The name of the station.
     */
    public Station(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
