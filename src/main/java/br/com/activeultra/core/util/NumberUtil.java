package br.com.activeultra.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Created by thiago.paiva on 20/12/2022
 */
public class NumberUtil {

    public static String removeNonNumeric(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return "";
        }
        return input.replaceAll("[^0-9]", "");
    }

    public static int multiply(final int numero, final int multiplicador) {
        if (!isValorNumericoPositivo(numero) || !isValorNumericoPositivo(multiplicador)) {
            return 0;
        }

        return numero * multiplicador;
    }

    public static BigDecimal multiply(final BigDecimal numero, final Integer multiplicador) {
        if (!isValorNumericoPositivo(numero) || !isValorNumericoPositivo(multiplicador)) {
            return BigDecimal.valueOf(0);
        }

        return numero.multiply(BigDecimal.valueOf(multiplicador));
    }

    public static BigDecimal multiply(final BigDecimal numero, final double multiplicador) {
        if (!isValorNumericoPositivo(numero) || !isValorNumericoPositivo(multiplicador)) {
            return BigDecimal.valueOf(0);
        }

        return numero.multiply(BigDecimal.valueOf(multiplicador));
    }

    public static double multiply(final double numero, final double multiplicador) {
        if (!isValorNumericoPositivo(numero) || !isValorNumericoPositivo(multiplicador)) {
            return 0;
        }

        return numero * multiplicador;
    }

    public static BigDecimal multiply(final BigDecimal numero, final Long multiplicador) {
        if (!isValorNumericoPositivo(numero) || !isValorNumericoPositivo(multiplicador)) {
            return BigDecimal.valueOf(0);
        }

        return numero.multiply(BigDecimal.valueOf(multiplicador));
    }

    public static BigDecimal multiply(final BigDecimal numero, final BigInteger multiplicador) {
        if (!isValorNumericoPositivo(numero) || !isValorNumericoPositivo(multiplicador)) {
            return BigDecimal.valueOf(0);
        }

        return numero.multiply(new BigDecimal(multiplicador));
    }

    public static Double divide(final Double valor, final Integer divisor) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(divisor)) {
            return (double) 0;
        }

        return valor / divisor;
    }

    public static BigDecimal divide(final BigDecimal valor, final Long divisor) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(divisor)) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal newValor = valor.setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal newDivisor = BigDecimal.valueOf(divisor).setScale(2, RoundingMode.HALF_DOWN);

        return newValor.divide(newDivisor, RoundingMode.CEILING);
    }

    public static BigDecimal divide(final BigDecimal valor, final BigDecimal divisor) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(divisor)) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal newValor = valor.setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal newDivisor = divisor.setScale(2, RoundingMode.HALF_DOWN);

        return newValor.divide(newDivisor, RoundingMode.CEILING);
    }

    public static BigDecimal divide(final BigDecimal valor, final Integer divisor) {
        return divide(valor, divisor, 2);
    }

    public static BigDecimal divide(final BigDecimal valor, final Integer divisor, final int scale) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(divisor)) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal newValor = valor.setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal newDivisor = BigDecimal.valueOf(divisor).setScale(scale, RoundingMode.HALF_DOWN);

        return newValor.divide(newDivisor, RoundingMode.CEILING);
    }

    /**
     * Arendonda com padrao 0-4 pra baixo, 5-9 pra cima
     */
    public static BigDecimal divideHalfUp(final BigDecimal valor, final Integer divisor, final int scale) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(divisor)) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal newValor = valor.setScale(scale, RoundingMode.HALF_UP);
        BigDecimal newDivisor = BigDecimal.valueOf(divisor).setScale(scale, RoundingMode.HALF_UP);

        return newValor.divide(newDivisor, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(final BigDecimal valor, final BigInteger divisor) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(divisor)) {
            return BigDecimal.valueOf(0);
        }

        BigDecimal newValor = valor.setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal newDivisor = new BigDecimal(divisor).setScale(2, RoundingMode.HALF_DOWN);

        return newValor.divide(newDivisor, RoundingMode.CEILING);
    }

    public static Integer getValor(Integer valor) {
        if (NumberUtil.isValorNumericoPositivo(valor)) {
            return valor;
        }

        return 0;
    }

    public static BigDecimal getValor(BigDecimal valor) {
        return getValor(valor, false, false);
    }

    public static BigDecimal getValorBigDecimal(Double valor) {
        if (NumberUtil.isValorNumericoPositivo(valor)) {
            return new BigDecimal(valor);
        }

        return BigDecimal.ZERO;
    }

    public static Integer getValor(Integer valor, Integer valorAuternativo) {
        Integer valorRetorno = getValor(valor);

        return isValorNumericoPositivo(valorRetorno)
                ? valorRetorno
                : valorAuternativo;
    }

    public static BigDecimal getValor(BigDecimal valor, BigDecimal valorAuternativo) {
        BigDecimal valorRetorno = getValor(valor, false, false);

        return isValorNumericoPositivo(valorRetorno)
                ? valorRetorno
                : valorAuternativo;
    }

    public static BigDecimal getValor(BigDecimal valor, Boolean duasCasasDecimais) {
        return getValor(valor, duasCasasDecimais, false);
    }

    public static BigDecimal getValor(BigDecimal valor, Boolean duasCasasDecimais, Boolean aceitaNegativo) {
        if ((aceitaNegativo && NumberUtil.isValorNumericoTotal(valor)) || NumberUtil.isValorNumericoPositivo(valor)) {
            return duasCasasDecimais ?
                    valor.setScale(2, RoundingMode.HALF_DOWN)
                    : valor;
        }

        return duasCasasDecimais ?
                new BigDecimal(0).setScale(2, RoundingMode.HALF_DOWN)
                : new BigDecimal(0);
    }

    public static Double getValor(Double valor) {
        if (NumberUtil.isValorNumericoPositivo(valor)) {
            return valor;
        }

        return (double) 0;
    }

    public static double getValorDoublePrimitive(BigDecimal valor) {
        if (NumberUtil.isValorNumericoPositivo(valor)) {
            return valor.doubleValue();
        }

        return 0;
    }

    public static int getValorIntPrimitive(Integer valor) {
        if (NumberUtil.isValorNumericoPositivo(valor)) {
            return valor;
        }

        return 0;
    }

    public static int getValorIntPrimitive(BigInteger valor) {
        if (NumberUtil.isValorNumericoPositivo(valor)) {
            return valor.intValue();
        }

        return 0;
    }

    public static boolean isValorNumericoTotal(final Object valor) {
        return isValorNumerico(valor, false, true);
    }

    public static boolean isValorNumerico(final Object valor) {
        return isValorNumerico(valor, false, false);
    }

    public static boolean isValorNumericoPositivo(final Object valor) {
        return isValorNumerico(valor, true, false);
    }

    public static boolean isValorNumerico(final Object valor, boolean isValidaPositivo, boolean isAceitaNegativo) {
        try {
            if (valor == null) {
                return false;
            }

            if (valor instanceof Integer) {
                return isAceitaNegativo || (isValidaPositivo
                        ? ((Integer) valor > 0)
                        : ((Integer) valor >= 0));
            }

            if (valor instanceof Double) {
                return isAceitaNegativo || (isValidaPositivo
                        ? ((Double) valor > 0)
                        : ((Double) valor >= 0));
            }

            if (valor instanceof Float) {
                return isAceitaNegativo || (isValidaPositivo
                        ? ((Float) valor > 0)
                        : ((Float) valor >= 0));
            }

            if (valor instanceof Long) {
                return isAceitaNegativo || (isValidaPositivo
                        ? ((Long) valor > 0)
                        : ((Long) valor >= 0));
            }

            if (valor instanceof BigDecimal) {
                return isAceitaNegativo || (isValidaPositivo
                        ? (((BigDecimal) valor).compareTo(BigDecimal.ZERO) > 0)
                        : (((BigDecimal) valor).compareTo(BigDecimal.ZERO) >= 0));
            }

            if (valor instanceof BigInteger) {
                return isAceitaNegativo || (isValidaPositivo
                        ? (((BigInteger) valor).compareTo(BigInteger.ZERO) > 0)
                        : (((BigInteger) valor).compareTo(BigInteger.ZERO) >= 0));
            }
        } catch (final Exception e) {
            return false;
        }

        return false;
    }

    public static boolean isValorEntreFaixa(final Integer valor, final Integer inicioFaixa, final Integer finalFaixa) {
        if (!isValorNumericoTotal(valor) || !isValorNumericoTotal(inicioFaixa) || !isValorNumericoTotal(finalFaixa)) {
            return false;
        }

        return valor >= inicioFaixa && valor <= finalFaixa;
    }

    public static boolean isValorMaiorOuIgual(final Integer valorInicial, final Integer valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial >= valorFinal;
    }

    public static boolean isValorMenorOuIgual(final Integer valorInicial, final Integer valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial <= valorFinal;
    }

    public static boolean isValorMaior(final Integer valorInicial, final Integer valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial > valorFinal;
    }

    public static boolean isValorMaior(final Long valorInicial, final Integer valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial > valorFinal;
    }

    public static boolean isValorMaior(final Double valorInicial, final Double valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial > valorFinal;
    }

    public static boolean isValorMaior(final BigDecimal valorInicial, final BigDecimal valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial.compareTo(valorFinal) > 0;
    }

    public static boolean isValorMenor(final BigDecimal valorInicial, final BigDecimal valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial.compareTo(valorFinal) < 0;
    }

    public static boolean isValorMenorOuIgual(final BigDecimal valorInicial, final BigDecimal valorFinal) {
        if (!isValorNumericoTotal(valorInicial) || !isValorNumericoTotal(valorFinal)) {
            return false;
        }

        return valorInicial.compareTo(valorFinal) <= 0;
    }

    public static double subtraiPorcentagem(double valor, double porcentagem) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(porcentagem)) {
            if (isValorNumericoPositivo(valor)) {
                return valor;
            }

            return 0;
        }

        return valor - multiply(valor, divide(porcentagem, 100));
    }

    public static BigDecimal subtraiPorcentagem(BigDecimal valor, double porcentagem) {
        if (!isValorNumericoPositivo(valor) || !isValorNumericoPositivo(porcentagem)) {
            if (isValorNumericoPositivo(valor)) {
                return valor;
            }

            return new BigDecimal(0);
        }

        return valor.subtract(multiply(valor, divide(porcentagem, 100)));
    }

    public static BigDecimal somar(final BigDecimal valor1, final BigDecimal valor2) {
        return NumberUtil.getValor(valor1, false, true)
                .add(NumberUtil.getValor(valor2, false, true));
    }

    public static BigDecimal subtrair(final BigDecimal valor1, final BigDecimal valor2) {
        return NumberUtil.getValor(valor1, false, true)
                .subtract(NumberUtil.getValor(valor2, false, true));
    }

    public static int arredondarParaCima(final BigDecimal valor) {
        return valor.setScale(0, RoundingMode.CEILING).intValue();
    }

    public static int arredondarParaBaixo(final BigDecimal valor) {
        return valor.setScale(0, RoundingMode.DOWN).intValue();
    }
}
