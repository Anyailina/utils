package org.annill;

public class DataStatistics {
    private long count = 0;
    private double sumDouble = 0;
    private double minDouble = Double.MAX_VALUE;
    private double maxDouble = Double.MIN_VALUE;
    private long sumLong = 0;
    private long minLong = Long.MAX_VALUE;
    private long maxLong = Long.MIN_VALUE;
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = 0;

    private static final String SHORT_STATS_TEMPLATE = " Счёт: %d";
    private static final String FULL_STATS_INT_TEMPLATE = " Счёт: %d, Минимум: %d, Максимум: %d, Сумма: %d";
    private static final String FULL_STATS_DOUBLE_TEMPLATE = " Счёт: %d, Минимум: %.2f, Максимум: %.2f, Сумма: %.2f, Средняя: %.2f";
    private static final String LENGTH_STATS_TEMPLATE = " Минимальная длина: %d, Максимальная длина: %d";

    public void addShortStatistics() {
        count++;
    }

    public void addFullStatistics(String value) {
        if (value.matches(RegexPattern.Int.getPattern())) {
            long number = Long.parseLong(value);
            sumLong += number;
            minLong= Math.min(minLong, number);
            maxLong = Math.max(maxLong, number);
        } else if (value.matches(RegexPattern.Float.getPattern())) {
            double number = Double.parseDouble(value);
            sumDouble += number;
            minDouble = Math.min(minDouble, number);
            maxDouble = Math.max(maxDouble, number);
        } else {
            int length = value.length();
            minLength = Math.min(minLength, length);
            maxLength = Math.max(maxLength, length);
        }
    }

    public String getShortStatistics() {
        return String.format(SHORT_STATS_TEMPLATE, count);
    }

    public String getFullStatistics() {
        StringBuilder sb = new StringBuilder(getShortStatistics());
        if (minDouble != Double.MAX_VALUE) {
            sb.append(String.format(FULL_STATS_DOUBLE_TEMPLATE, minDouble, maxDouble, sumDouble, sumDouble / count));
        }
        if (maxLong != Integer.MAX_VALUE) {
            sb.append(String.format(FULL_STATS_INT_TEMPLATE, minLong, maxLong, sumLong, sumLong / count));
        }
        if (minLength != Integer.MAX_VALUE) {
            sb.append(String.format(LENGTH_STATS_TEMPLATE, minLength, maxLength));
        }
        return sb.toString();
    }
}