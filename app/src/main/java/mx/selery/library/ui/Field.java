package mx.selery.library.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 09/02/2016.
 */
public class Field implements IValidator {

    private TextView control;
    private Boolean required;
    private List<IValueValidator> valueValidatorList;//TODO: no tiene caso que esto sea una lista
    private Drawable errorIcon;
    private String errorMessage;
    private Context context;

    private int backGroundResourceID;
    public int getBackGroundResourceID() {
        return backGroundResourceID;
    }
    public void setBackGroundResourceID(int backGroundResourceID) {
        this.backGroundResourceID = backGroundResourceID;
    }

    private int errorBackGroundResourceID;
    public int getErrorBackGroundResourceID() {
        return errorBackGroundResourceID;
    }
    public void setErrorBackGroundResourceID(int errorBackGroundResourceID) {
        this.errorBackGroundResourceID = errorBackGroundResourceID;
    }

    public Field(Context context)
    {
        this.context=context;
        required=true;
        valueValidatorList= new ArrayList<IValueValidator>();
    }

    public Field(TextView tv, Boolean required, Context context)
    {
        this(tv, required, null,context);
    }

    public Field(TextView tv, Boolean required, IValueValidator validator, Context context)
    {
        this(tv, required, null, validator,context);
    }

    public Field(TextView tv, Boolean required, Drawable errorIcon,IValueValidator validator, Context context)
    {
        this(context);
        control = tv;
        this.required = required;
        this.errorIcon = errorIcon;
        if (validator != null)
            this.valueValidatorList.add(validator);

        this.control.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                focusChange(hasFocus);
            }
        });

        this.control.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                textChanged(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

    }

    public Boolean validate()
    {
        String value = getValue();

        if (this.required)
        {
            if (TextUtils.isEmpty(value))
            {
                this.errorMessage = StringHelper.getValueFromResourceCode("misc_required_field",this.context);
                setError();
                return false;
            }

        }

        for(IValueValidator validator:valueValidatorList)
        {
            Boolean result = validator.validateValue(getValue());
            if(!result)
            {
                this.errorMessage = validator.getErrorMessage (this.context);
                setError();

                return false;//salir cuando el primero falla
            }
        }

        return true;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public TextView getControl()
    {
        return this.control;
    }

    private void  focusChange(Boolean hasFocus)
    {
        if (!hasFocus)
        {
            validate();
        }
        else
        {
            this.control.setBackgroundResource(backGroundResourceID);
        }
    }

    private void textChanged(Editable s)
    {
        this.control.setBackgroundResource(backGroundResourceID);
    }

    private String getValue() {
        return this.control.getText().toString();
    }

    private void setError()
    {
        this.control.setBackgroundResource(errorBackGroundResourceID);
    }

}
