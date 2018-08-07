package org.maestro.results.main.actions.record.utils;

public class PrintUtils {
    public static void printCreatedKey(final String title, int key) {
        System.out.println("New " + title + " created with key: " + key);
    }

    public static void printCreatedRecord(final String title) {
        System.out.println("New " + title + " created");
    }

}
