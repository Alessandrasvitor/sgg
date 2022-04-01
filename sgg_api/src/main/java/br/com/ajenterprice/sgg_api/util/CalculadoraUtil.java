package br.com.ajenterprice.sgg_api.util;

import java.math.BigDecimal;
import java.util.Collection;

public class CalculadoraUtil {

    public static final int NUMERO_100 = 100;
    public static final int PRECISAO_2_DECIMAIS = 2;
    public static final int PRECISAO_1_DECIMAIS = 1;
    public static final int INT = 1000;

    /**
     * Para a classe nunca ser instanciada.
     */
    private CalculadoraUtil() {
    }

    /**
     * Calcula a soma dos parâmetros. Considera valores <b>null</b> como valor
     * 0.0.
     *
     * @param valores {@link BigDecimal}
     * @return somatório {@link BigDecimal}
     */
    public static BigDecimal somar(BigDecimal... valores) {
        BigDecimal somatorio = BigDecimal.ZERO;
        for (BigDecimal valor : valores) {
            if (valor != null) {
                somatorio = somatorio.add(valor);
            }
        }
        return somatorio;
    }

    /**
     * Calcula a subtração dos @param valores do @param valorInicial
     * 0.0.
     *
     * @param valorInicial {@link BigDecimal}
     * @param valores      {@link BigDecimal}
     * @return somatório {@link BigDecimal}
     */
    public static BigDecimal subtrair(BigDecimal valorInicial, BigDecimal... valores) {
        BigDecimal valorParaSubtrair = valorInicial;
        for (BigDecimal valor : valores) {
            if (valor != null) {
                valorParaSubtrair = valorParaSubtrair.subtract(valor);
            }
        }
        return valorParaSubtrair;
    }

    /**
     * Calcula o resultado da multiplicação entre os valores passados por
     * parâmetro.
     *
     * @param valores {@link BigDecimal}
     * @return resultado {@link BigDecimal}
     */
    public static BigDecimal multiplicar(BigDecimal... valores) {
        BigDecimal resultado = BigDecimal.ONE;
        for (BigDecimal valor : valores) {
            resultado = resultado.multiply(valor);
        }
        return resultado;
    }

    /**
     * Retorna o resultado da divisão.
     *
     * @param dividendo o valor que será dividido
     * @param precisao  precisao
     * @param divisor   quantas partes o dividendo será dividido
     * @return dividendo / divisor
     */
    public static BigDecimal dividir(BigDecimal dividendo, int precisao, BigDecimal divisor) {
        if (BigDecimal.ZERO.equals(divisor)) {
            return null;
        }
        return dividendo.divide(divisor, precisao, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calcula o percentual do valor.
     *
     * @param valor             valor ao qual será submetido o percentual
     * @param percentualDoValor percentual desejado sobre o valor
     * @return resultado {@link BigDecimal}
     */
    public static BigDecimal porcentagem(BigDecimal valor, Integer percentualDoValor) {
        BigDecimal percentual = dividir(new BigDecimal(percentualDoValor), PRECISAO_2_DECIMAIS, new BigDecimal(NUMERO_100));
        return multiplicar(valor, percentual);
    }

    /**
     * Calcula o percentual do valor.
     *
     * @param valor             valor ao qual será submetido o percentual
     * @param percentualDoValor percentual desejado sobre o valor
     * @return resultado {@link BigDecimal}
     */
    public static BigDecimal porcentagem(BigDecimal valor, BigDecimal percentualDoValor) {
        BigDecimal percentual = dividir(percentualDoValor, PRECISAO_2_DECIMAIS, new BigDecimal(NUMERO_100));
        return multiplicar(valor, percentual);
    }

    /**
     * Calcular a media de valores numa lista de BigDecimal
     *
     * @param pValores Collection BigDecimal
     * @return {@link BigDecimal}
     */
    public static BigDecimal media(Collection<BigDecimal> pValores) {
        BigDecimal reduce = pValores.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return dividir(reduce, PRECISAO_2_DECIMAIS, BigDecimal.valueOf(pValores.size()));

    }

}
