package mx.selery.library.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by htorres on 10/02/2016.
 */
public class Encryption {

    public static  byte[] EncryptToByteArray(String s)throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        /*MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(s.getBytes("iso-8859-1"), 0, s.length());
        byte[] sha1hash = md.digest();
        return sha1hash;*/
        return s.getBytes();
    }

}
