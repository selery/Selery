package mx.selery.library.ui;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 09/02/2016.
 */
public class PasswordFieldRule implements IValidator {

    private TextView password;
    private TextView confirmPassword;
    private String errorMessage=null;
    private Context context;

    public PasswordFieldRule(TextView password, TextView confirmPassword, Context context)
    {
        this.password=password;
        this.confirmPassword = confirmPassword;
        this.context=context;
    }


    public Boolean validate()
    {
        String password = this.password.getText().toString();
        String confirmPassword = this.confirmPassword.getText().toString();
        if (password.equals(confirmPassword))
        {
            errorMessage=null;
            return true;
        }

        errorMessage = StringHelper.getValueFromResourceCode("registration_password_mismatch", this.context);
        return false;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public TextView getControl()
    {
        return confirmPassword;
    }
}
