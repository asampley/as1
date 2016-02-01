package sampley.sampley_fueltrack;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Implements the {@link TextWatcher} interface, for the use of auto-formatting dates, in the form
 * YYYY-MM-DD, by inserting dashes automatically after the numbers are printed, and by removing the
 * dashes automatically when backspaced.
 */
public class DateTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        // first check if the format is correct
        boolean formatted = true;

        // make sure the string does not end in dashes
        if (s.length() == 5 || s.length() == 8) {
            formatted = false;
        }

        for (int i = 0; i < s.length() && formatted; ++i) {
            if (i == 4 || i == 7) {
                // check for dashes
                if (s.charAt(i) != '-') {
                    formatted = false;
                    break;
                }
            } else {
                if (!Character.isDigit(s.charAt(i))) {
                    formatted = false;
                    break;
                }
            }
        }

        if (!formatted) {
            // start putting in dashes from the end to the start
            StringBuffer stringBuffer = new StringBuffer(s.toString().replaceAll("-",""));
            if (stringBuffer.length() > 6) stringBuffer.insert(6, "-");
            if (stringBuffer.length() > 4) stringBuffer.insert(4, "-");
            s.clear();
            s.append(stringBuffer);
        }
    }
}
