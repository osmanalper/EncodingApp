package encoding.util;

import java.math.BigInteger;

/**
 * Created by osmanalper on 17/01/16.
 */
public class UtilEncoding {

    public static boolean isStringHex(String encodeValue) {
        boolean isHex = false;
        try{
            new BigInteger(encodeValue, 16);
            isHex = true;
        }
        catch(NumberFormatException ex) { }
        return isHex;
    }

    public static String convertHexToString(String hex) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hex.length() ; i = i + 2) {

            String sub = hex.substring(i,i+2);
            output.append((char) Integer.parseInt(sub, 16));
        }

        return output.toString();
    }

    public static String convertStringToHex(String str){
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }
        return hex.toString();
    }

    public static String getLSBForHex(String s){
        char[] stringArr = s.toCharArray();
        String retval = "";
        for (int i = stringArr.length - 1; i >= 0 ; i = i - 2) {
            if((i - 1) >= 0) {
                retval+=stringArr[i-1];
                retval+=stringArr[i];
            }
        }
        return retval;
    }

}
