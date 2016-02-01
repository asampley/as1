package sampley.sampley_fueltrack.MVC;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2016-01-27.
 */
public abstract class MVCModel {
    List<MVCView> mvcViews;

    public MVCModel() {
        mvcViews = new ArrayList<>();
    }
    public void addView(MVCView v) {
        mvcViews.add(v);
        Log.println(Log.DEBUG, "MVCModel", "Added new view of type " + v.getClass().getSimpleName());
    }
    public void removeView(MVCView v) {
        if (mvcViews.remove(v)) {
            Log.println(Log.DEBUG, "MVCModel", "Removed old view of type " + v.getClass().getSimpleName());
        }
    }
    public void notifyViews() {
        Log.println(Log.DEBUG, "MVCModel", "Notifying Views");
        for (MVCView view : mvcViews) {
            view.update(this);
            Log.println(Log.DEBUG, "MVCModel", "Notified view of type " + view.getClass().getSimpleName());
        }
    }
}
