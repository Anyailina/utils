package org.annill;

public enum RegexPattern {
    Int("-?\\d+"),
    Float("-?\\d*\\.\\d+([eE]-?\\d+)?");


    RegexPattern(String pattern) {
        this.pattern = pattern;
    }

    private String pattern;

    public String getPattern() {
        return pattern;
    }
}
