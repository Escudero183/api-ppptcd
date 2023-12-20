package gob.pe.devida.ppptcd.utils;

import java.text.Normalizer;
import java.util.Random;

/**
 * File created by Linygn Escudero$ on 12/11/23$
 */
public class StringUtil {

    public static String normalizedJson(String json) {
        return Normalizer.normalize(json, Normalizer.Form.NFC);
    }

    public String getAlphaNumeric(int len) {
        char[] ch = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        char[] c=new char[len];
        Random random=new Random();
        for (int i = 0; i < len; i++) {
            c[i]=ch[random.nextInt(ch.length)];
        }
        return new String(c);
    }
}
