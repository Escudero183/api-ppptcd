package gob.pe.devida.ppptcd.utils;

import java.text.Normalizer;

/**
 * File created by Linygn Escudero$ on 12/11/23$
 */
public class StringUtil {

    public static String normalizedJson(String json) {
        return Normalizer.normalize(json, Normalizer.Form.NFC);
    }
}
