package sampley.sampley_fueltrack.MVC;

/**
 * Created by A on 2016-01-27.
 */
public interface MVCView<Model extends MVCModel> {
    public void update(Model m);
}
