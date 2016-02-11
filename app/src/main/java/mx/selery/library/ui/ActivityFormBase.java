package mx.selery.library.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htorres on 09/02/2016.
 */
public abstract class ActivityFormBase extends ActivityBase {

    protected List<IValidator> ruleSet = new ArrayList<IValidator>();

    public @Override void setContentView(int viewid)
    {
        super.setContentView(viewid);
        initializeFormFields();
    }

    protected abstract void initializeFormFields();

    protected void addValidator(IValidator v)	{
        ruleSet.add(v);
    }

    protected Boolean validateForm()
    {
        Boolean finalResult = true;
        for(IValidator v:ruleSet)
        {
            Boolean result = v.validate();
            if (result == false)
            {
                finalResult = false;
            }
        }
        return finalResult;
    }


}
