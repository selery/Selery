package mx.selery.library.ui;

import android.content.Context;

/**
 * Created by htorres on 09/02/2016.
 */
public interface IValueValidator {
    Boolean validateValue(String value);
    String getErrorMessage(Context context);
}
