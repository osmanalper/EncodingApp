package encoding.service;

import encoding.util.UtilEncoding;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by osmanalper on 11/01/16.
 */
@Service
public class EncodingBase29 implements Encoding{

    private String base29Mapping = "abcdefghijklmnopqrstuvwxyz123";
    private BigInteger big29 = BigInteger.valueOf(29);
    private boolean isHexEncoded;

    public String encode(String encodeValue){
        boolean isHex = UtilEncoding.isStringHex(encodeValue);
        String encodedValue;
        if(isHex){
            encodedValue = encodeUsingHex(encodeValue);
            isHexEncoded = true;
        } else {
            encodeValue = encodeValue.replace("+"," ");
            String s = UtilEncoding.convertStringToHex(encodeValue);
            encodedValue = encodeUsingHex(s);
            isHexEncoded = false;
        }
        return encodedValue;
    }

    public String decode(String encodedValue){
        int numberOfChunksForBase29 = encodedValue.length() / 9;
        int remainingByte = encodedValue.length() % 9;
        String decodedValue = "";
        for(int i = 0; i < numberOfChunksForBase29; i++) {
            String chunk = encodedValue.substring(i * 9, (i + 1) * 9);
            BigInteger decimal = base29ToDecimal(chunk);
            String chunkLSB = UtilEncoding.getLSBForHex(decimal.toString(16));
            decodedValue+=chunkLSB.toUpperCase();
        }
        if(remainingByte > 0) {
            String chunk = encodedValue.substring(numberOfChunksForBase29 * 9);
            BigInteger decimal = base29ToDecimal(chunk);
            String remainingChunkLSB = UtilEncoding.getLSBForHex(decimal.toString(16));
            decodedValue+=remainingChunkLSB.toUpperCase();
        }
        if(isHexEncoded){
            return decodedValue;
        } else {
            return UtilEncoding.convertHexToString(decodedValue);
        }
    }

    private String encodeUsingHex(String encodeValue) {
        int numberOfChunksForHex = encodeValue.length() / 10;
        int remainingByte = encodeValue.length() % 10;
        String encodedValue = "";
        for(int i = 0; i < numberOfChunksForHex; i++) {
            String chunk = encodeValue.substring(i*10, (i+1)*10);
            String chunkLSB = UtilEncoding.getLSBForHex(chunk);
            BigInteger decimalValue = new BigInteger(chunkLSB,16);
            encodedValue += decimalToBase29(decimalValue);
        }
        if(remainingByte > 0) {
            String chunk = encodeValue.substring(numberOfChunksForHex * 10);
            for(int i = 0; i < (10 - remainingByte); i++) {
                chunk += "0";
            }
            String remainingChunkLSB = UtilEncoding.getLSBForHex(chunk);
            BigInteger decimalValue = new BigInteger(remainingChunkLSB,16);
            encodedValue+= decimalToBase29(decimalValue);
        }
        return encodedValue;
    }

    private String decimalToBase29(BigInteger decimalValue) {
        String encodedValue = "";
        do {
            encodedValue += base29Mapping.charAt(decimalValue.mod(big29).intValue());
            decimalValue = decimalValue.divide(big29);
        }
        while(decimalValue.compareTo(big29)>=0);
        if(decimalValue.compareTo(big29)<0){
            encodedValue += base29Mapping.charAt(decimalValue.intValue());
            encodedValue += base29Mapping.charAt(decimalValue.divide(big29).intValue());
        }
        return encodedValue;
    }

    private BigInteger base29ToDecimal(String encodedValue){
        BigInteger decimal = BigInteger.valueOf(0);
        for (int i = 0; i < encodedValue.length(); i++) {
            BigInteger index = BigInteger.valueOf(base29Mapping.indexOf(encodedValue.charAt(i)));
            BigInteger pow = big29.pow(i);
            decimal = decimal.add(index.multiply(pow));
        }
        return decimal;
    }
}

