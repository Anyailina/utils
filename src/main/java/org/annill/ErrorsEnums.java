package org.annill;

public enum ErrorsEnums {
    NOT_FIND_VALUE_FOR_OPTION_O("Отсутствует значение для опции -o"),
    NOT_FIND_VALUE_FOR_OPTION_P("Отсутствует значение для опции -p"),
    NOT_FIND_OPTION("Неизвестная опция: "),
    NOT_FIND_INPUT_FILE("Не найдено входных файлов."),
    MISTAKE_READING_FILE("Ошибка чтения файла"),
    UNKNOWN("Неизвестная ошибка");

    private final String errors;

    ErrorsEnums(String errors) {
        this.errors = errors;
    }

    public String getErrors() {
        return errors;
    }


}