package com.test.logging.common;

public enum TestFactoryType {

    LITERAL("String literal"),
    LEVEL_CHECKED_LITERAL("Level checked string literal"),
    CONCAT_STRING("'+' concatenation"),
    SLF4J_FORMATTED_STRING("SLF4J formatting"),
    JAVA_FORMATTED_STRING("String.format");

    /** String representation of the factory type */
    private final String name;

    private TestFactoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
