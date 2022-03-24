package br.com.ajenterprice.sgg_api.util;

public class GeralUtil {

    public static Boolean stringNullOrEmpty(String valor ){
        return valor == null || valor.isEmpty() || valor.length() == 0;
    }
}
