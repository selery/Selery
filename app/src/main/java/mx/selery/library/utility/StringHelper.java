package mx.selery.library.utility;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by htorres on 10/02/2016.
 */
public class StringHelper {

    public static String getValueFromResourceCode(String code, Context context)
    {
        int i = context.getResources().getIdentifier(code, "string", context.getPackageName());
        return  context.getString(i);
    }

    public static String toShortDateString(String d, Context context) throws ParseException
    {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        SimpleDateFormat sformat = new SimpleDateFormat(((SimpleDateFormat) dateFormat).toPattern());
        return dateFormatter.format(sformat.parse(d));
    }

}
