package mx.selery.library.utility;
import android.app.Activity;
import android.content.Context;

/**
 * Created by htorres on 10/02/2016.
 */
public class StringHelper {

    public static String getValueFromResourceCode(String code, Context context)
    {
        int i = context.getResources().getIdentifier(code, "string", context.getPackageName());
        return  context.getString(i);
    }
}
