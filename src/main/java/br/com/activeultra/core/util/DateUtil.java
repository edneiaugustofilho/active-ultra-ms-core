package br.com.activeultra.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("unused")
public class DateUtil {

    public static final Locale localeDefault = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
    public static final String DATE_FORMAT_PT_BR = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DB = "yyyy-MM-dd";

    private static final SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy", localeDefault);
    private static final SimpleDateFormat sigilMonthYearFormat = new SimpleDateFormat("MMM yyyy", localeDefault);

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDate todayAmericaSaoPaulo() {
        return LocalDate.now();
    }

    public static LocalDateTime now() {
        return now(ZoneId.of("UTC-3"));
    }

    public static LocalDateTime now(ZoneId timeZone) {
        return LocalDateTime.now().atZone(timeZone).toLocalDateTime();
    }

    public static String getDay(LocalDate date) {
        String dia = "";

        if (!Objects.isNull(date)) {
            dia = String.valueOf(date.getDayOfMonth());
        }

        return dia;
    }

    public static Integer getMonth(LocalDate date) {
        Integer month = null;

        if (!Objects.isNull(date)) {
            month = date.getMonth().getValue();
        }

        return month;
    }

    public static Integer getMonth() {
        return LocalDate.now().getMonth().getValue();
    }

    public static String getMonthYearPtBr(LocalDate date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("America/Sao_Paulo"));

        String month = zonedDateTime.getMonth().name();
        int year = zonedDateTime.getYear();
        
        return month + " de " + year;
    }

    public static String getSemester(LocalDate date) {
        if (date.getMonthValue() <= 6) {
            return "1";
        } else {
            return "2";
        }
    }

    public static int getYear(LocalDate date) {
        return date.getYear();
    }

    public static int getYear() {
        return LocalDate.now().getYear();
    }

    public static LocalDateTime addMinutes(LocalDateTime date, int minutos) {
        return date.plusMinutes(minutos);
    }

    public static int addYer(LocalDate date, int qntd) {
        return date.getYear() + qntd;
    }

    public static LocalDate addMonth(LocalDate date, int qntd) {
        return date.plusMonths(qntd);
    }

    public static String formatDateTime(LocalDateTime date) {
        return formatDateTime(date, DateFormat.SHORT, DateFormat.SHORT);
    }

    public static String formatDateTime(LocalDateTime date, int dateStyle, int timeStyle) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, localeDefault);
        return Objects.nonNull(date) ? dateFormat.format(date) : "";
    }

    public static String formatDate(LocalDate date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(LocalDate date) {
        return formatDate(date, DateFormat.SHORT);
    }

    public static String formatDate(LocalDate date, int style) {
        String valorFormatado = "";

        if (!Objects.isNull(date)) {
            DateFormat dateFormat = DateFormat.getDateInstance(style, localeDefault);
            valorFormatado = dateFormat.format(date);
        }

        return valorFormatado;
    }

    public static String formatHour(LocalDateTime date) {
        return formatHour(date, DateFormat.SHORT);
    }

    public static String formatHour(LocalDateTime date, int style) {
        String valorFormatado = "";

        if (!Objects.isNull(date)) {
            DateFormat dateFormat = DateFormat.getTimeInstance(style, localeDefault);
            valorFormatado = dateFormat.format(date);
        }

        return valorFormatado;
    }

    public static Integer getHourAsIntegerFromDate(LocalDateTime date) {
        Integer valor = null;

        String formatarHora = formatHour(date);
        if (Objects.nonNull(formatarHora)) {
            valor = Integer.parseInt(formatarHora.replace(":", ""));
        }

        return valor;
    }

    public static Integer getHourMinuteSecondeAsIntegerFromDate(LocalDateTime date) {
        Integer valor = null;

        String formatarHora = formatHour(date, DateFormat.LONG);
        if (Objects.nonNull(formatarHora)) {
            valor = Integer.parseInt(formatarHora.replace(":", ""));
        }

        return valor;
    }

    public static String getMonthNamePtBr(LocalDate date) {
        return getMonthNamePtBr(date, false);
    }

    public static String getMonthNamePtBr(LocalDate date, boolean abreviado) {
        if (Objects.nonNull(date)) {
            int intMes = date.getMonthValue();

            return extrairNomeMesPtBr(intMes, abreviado);
        }

        return null;
    }

    public static String getMonthNamePtBr(int indexMes) {
        return getMonthNamePtBr(indexMes, false);
    }

    public static String getMonthNamePtBr(int indexMes, boolean abreviado) {
        return extrairNomeMesPtBr(indexMes, abreviado);
    }

    private static String extrairNomeMesPtBr(int indexMes, boolean abreviado) {
        return switch (indexMes) {
            case 1 -> (abreviado ? "jan" : "janeiro");
            case 2 -> (abreviado ? "fev" : "fevereiro");
            case 3 -> (abreviado ? "mar" : "marco");
            case 4 -> (abreviado ? "abr" : "abril");
            case 5 -> (abreviado ? "mai" : "maio");
            case 6 -> (abreviado ? "jun" : "junho");
            case 7 -> (abreviado ? "jul" : "julho");
            case 8 -> (abreviado ? "ago" : "agosto");
            case 9 -> (abreviado ? "set" : "setembro");
            case 10 -> (abreviado ? "out" : "outubro");
            case 11 -> (abreviado ? "nov" : "novembro");
            case 12 -> (abreviado ? "dez" : "dezembro");
            default -> "";
        };
    }

    public static String getNomeDiaSemana(LocalDate date) {
        return getNomeDiaSemana(date, false);
    }

    public static String getNomeDiaSemana(LocalDate date, Boolean abreviado) {
        String diaSemana = "";

        if (!Objects.isNull(date)) {
            return switch (date.getDayOfWeek()) {
                case MONDAY -> (abreviado ? "seg" : "segunda");
                case TUESDAY -> (abreviado ? "ter" : "terça");
                case WEDNESDAY -> (abreviado ? "qua" : "quarta");
                case THURSDAY -> (abreviado ? "qui" : "quinta");
                case FRIDAY -> (abreviado ? "sex" : "sexta");
                case SATURDAY -> (abreviado ? "sab" : "sabado");
                case SUNDAY -> (abreviado ? "dom" : "domingo");
            };
        }

        return diaSemana;
    }

    public static LocalDateTime getLastHourFromDate(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 23, 59, 59);
    }

    public static LocalDateTime getFirstHourFromDate(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0, 0);
    }

    public static LocalDateTime getFirstDayFromDate(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), 1, 0, 0, 0);
    }

    public static LocalDate getLastDayFromDate(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());
    }

    public static LocalTime zeroHour() {
        return LocalTime.of(0, 0, 0);
    }

    public static LocalTime lastHour() {
        return LocalTime.of(23, 59, 59);
    }

    public static Integer getDaysDifference(LocalDate dataInicial, LocalDate dataFinal) {
        return Math.toIntExact(ChronoUnit.DAYS.between(dataInicial, dataFinal));
    }

    public static LocalDate addDaysToDate(LocalDate date, int dias) {
        return date.plusDays(dias);
    }

    public static int getHoursDifference(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return Math.toIntExact(ChronoUnit.HOURS.between(dateStart, dateEnd));
    }

    public static int getMinutesDifference(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return Math.toIntExact(ChronoUnit.MINUTES.between(dateStart, dateEnd));
    }

    public static LocalDate parseLocalDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return convertStringToLocalDate(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: " + value, e);
        }
    }


    public static LocalDate convertStringToLocalDate(String date) throws ParseException {
        return convertStringToLocalDate(date, DATE_FORMAT_DB);
    }

    public static LocalDate convertStringToLocalDate(String date, String dateFormat) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String toFormattedDateString(LocalDate date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }

    public static String toFormattedDateTimeString(LocalDateTime date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public static String getFormattedMonthYear(LocalDate date) {
        String mesAno = "";

        if (Objects.nonNull(date)) {
            mesAno = sigilMonthYearFormat.format(date).toUpperCase();
        }

        return mesAno;
    }

    public static boolean isWeekend(LocalDateTime localDateTime) {
        return isWeekend(localDateTime.toLocalDate());
    }

    public static boolean isWeekend(LocalDate localDate) {
        List<DayOfWeek> finaisDeSemana = new ArrayList<>();
        finaisDeSemana.add(DayOfWeek.FRIDAY);
        finaisDeSemana.add(DayOfWeek.SATURDAY);
        finaisDeSemana.add(DayOfWeek.SUNDAY);

        return finaisDeSemana.contains(localDate.getDayOfWeek());
    }

    public static boolean isBusinessDay(LocalDate localDate) {
        boolean isSabado = localDate.getDayOfWeek() == DayOfWeek.SATURDAY;
        boolean isDomingo = localDate.getDayOfWeek() == DayOfWeek.SUNDAY;

        return !(isSabado || isDomingo);
    }

    public static boolean isToday(DayOfWeek dayOfWeek) {
        return LocalDate.now().getDayOfWeek() == dayOfWeek;
    }

    public static boolean isMonday() {
        return isToday(DayOfWeek.MONDAY);
    }

    public static boolean isTuesDay() {
        return isToday(DayOfWeek.TUESDAY);
    }

    public static boolean isWednesday() {
        return isToday(DayOfWeek.WEDNESDAY);
    }

    public static boolean isThursday() {
        return isToday(DayOfWeek.THURSDAY);
    }

    public static boolean isFriday() {
        return isToday(DayOfWeek.FRIDAY);
    }

    public static boolean isSaturday() {
        return isToday(DayOfWeek.SATURDAY);
    }

    public static boolean isSunday() {
        return isToday(DayOfWeek.SUNDAY);
    }

    public static boolean isCurrentTimeAfterOrEqual(int hora, int minuto) {
        return LocalDateTime.now().toLocalTime().isAfter(LocalTime.of(hora, minuto)) ||
                LocalDateTime.now().toLocalTime().equals(LocalTime.of(hora, minuto));
    }

    public static boolean isCurrentTimeBeforeOrEqual(int hora, int minuto) {
        return LocalDateTime.now().toLocalTime().isBefore(LocalTime.of(hora, minuto)) ||
                LocalDateTime.now().toLocalTime().equals(LocalTime.of(hora, minuto));
    }

    public static LocalDate addBusinessDays(LocalDate localDate, int qtde) {
        if (Objects.isNull(localDate)) {
            return null;
        }

        int qtdeDiasUteisSomados = 0;
        while (qtdeDiasUteisSomados < qtde) {
            localDate = localDate.plusDays(1);
            if (DateUtil.isBusinessDay(localDate)) {
                qtdeDiasUteisSomados++;
            }
        }

        return localDate;
    }

    public static LocalDate subBusinessDay(LocalDate localDate, int qtde) {
        if (Objects.isNull(localDate)) {
            return null;
        }

        int qtdeDiasUteisSomados = 0;
        while (qtdeDiasUteisSomados < qtde) {
            localDate = localDate.minusDays(1);
            if (DateUtil.isBusinessDay(localDate)) {
                qtdeDiasUteisSomados++;
            }
        }

        return localDate;
    }
}
