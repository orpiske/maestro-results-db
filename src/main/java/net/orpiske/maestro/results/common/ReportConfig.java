package net.orpiske.maestro.results.common;

import net.orpiske.mpt.common.ConfigurationWrapper;
import org.apache.commons.configuration.AbstractConfiguration;

import java.util.Arrays;

public class ReportConfig {
    private static final AbstractConfiguration config = ConfigurationWrapper.getConfig();

    public static int[] getIntArrayForTest(final String testName, final String configuration) {
        String[] values = config.getStringArray(testName + "." + configuration);

        return Arrays.stream(values).mapToInt(v -> Integer.parseInt(v)).toArray();
    }


    public static boolean[] getBooleanArray(final String testName, final String configuration) {
        String[] values = config.getStringArray(testName + "." + configuration);

        boolean[] ret = new boolean[values.length];

        int i = 0;
        for (String value : values) {
            ret[i] = Boolean.parseBoolean(value);
            i++;
        }

        return ret;
    }

    public static String getJoinedString(final String testName, final String configuration) {
        String values[] = config.getStringArray(testName + "." + configuration);

        return String.join(",", values);
    }

    public static String getString(final String testName, final String configuration) {
        return config.getString(testName + "." + configuration);
    }

    public static Integer getInteger(final String testName, final String configuration) {
        String tmp = config.getString(testName + "." + configuration);

        return Integer.parseInt(tmp);
    }
}
