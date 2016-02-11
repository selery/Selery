package mx.selery.library.ui;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by htorres on 09/02/2016.
 */
public interface IValidator {
    Boolean validate();
    String getErrorMessage();
    TextView getControl ();
}
