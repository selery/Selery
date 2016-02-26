package mx.selery.library.utility;

/**
 * Created by htorres on 25/02/2016.
 */
public class HttpHelper {

    public enum HttpStatus
    {
        NotFound(404),OK(200);
        private final int value;
        private HttpStatus(int value)
        {
            this.value=value;
        }

        public int getValue() {
            return value;
        }


    }
}
