package br.com.enterprice.sgg.util;

import org.springframework.util.StringUtils;

import java.util.Date;

public class GeradorAleatorio {

    public static Date gerarTimeAleatorio(Long tempo){
        return new Date(tempo);
    }

    public static String gerarString(int qtdCaracter){

        String theAlphaNumericS;
        StringBuilder builder;

        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        //create the StringBuffer
        builder = new StringBuilder(qtdCaracter);

        for (int m = 0; m < qtdCaracter; m++) {

            // generate numeric
            int myindex
                    = (int)(theAlphaNumericS.length()
                    * Math.random());

            // add the characters
            builder.append(theAlphaNumericS
                    .charAt(myindex));
        }

        return builder.toString();
    }


}
