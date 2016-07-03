package mx.selery.library.utility;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by htorres on 02/07/2016.
 */
public class DateTimeHelper
{
    /*
    Recibe una fecha como string y la devuelve en formato short
    */
    public static String toShortDateString(String d, Context context) throws ParseException
    {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        SimpleDateFormat sformat = new SimpleDateFormat(((SimpleDateFormat) dateFormat).toPattern());
        return dateFormatter.format(sformat.parse(d));
    }

    /*
    Recibe  month, day, year y regresa la fecha short en el formato del cliente
    */
    public static String toShortDateString(int month, int day, int year,Context context)
    {
        char[] dateFormatOrder = android.text.format.DateFormat.getDateFormatOrder(context);
        int[] order = new int[3];
        switch (dateFormatOrder[0])
        {
            case 'd':
                order[0]=day;
                break;
            case 'M':
                order[0]=month;
                break;
            case 'y':
                order[0]=year;
                break;
        }

        switch (dateFormatOrder[1])
        {
            case 'd':
                order[1]=day;
                break;
            case 'M':
                order[1]=month;
                break;
            case 'y':
                order[1]=year;
                break;
        }

        switch (dateFormatOrder[2])
        {
            case 'd':
                order[2]=day;
                break;
            case 'M':
                order[2]=month;
                break;
            case 'y':
                order[2]=year;
                break;
        }

        return String.format("%1$02d/%2$02d/%3$d",order[0],order[1],order[2]);
    }
}
