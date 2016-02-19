package mx.selery.library.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by htorres on 10/02/2016.
 */
public class Encryption {

    //http://stackoverflow.com/questions/3934331/android-how-to-encrypt-a-string
    //Regresa 32 bytes
    public static byte[] EncryptToByteArray(String toEncrypt)throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        final MessageDigest digest = MessageDigest.getInstance("md5");
        digest.update(toEncrypt.getBytes());
        final byte[] bytes = digest.digest();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }

        return sb.toString().toLowerCase().getBytes();
    }


}
