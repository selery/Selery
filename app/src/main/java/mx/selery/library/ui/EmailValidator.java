package mx.selery.library.ui;

import android.content.Context;
import android.util.Patterns;

import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 09/02/2016.
 */
public class EmailValidator implements IValueValidator {
    @Override
    public Boolean validateValue(String value)
    {
        Boolean retVal=true;
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches())
            retVal=false;
        return retVal;
    }

    @Override
    public String getErrorMessage(Context context) {
        return StringHelper.getValueFromResourceCode("reg_invalid_email",context);
    }

}
