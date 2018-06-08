package org.maestro.results.main.actions.report;

public final class RenderUtils {
    public static String templateBasePath(final String templateName) {
        return "/net/orpiske/maestro/results/main/action/report/" + templateName + "/";
    }

    public static String templatedResourcePath(final String templateName, final String resourceName) {
        return templateBasePath(templateName) + "/" + resourceName;
    }

    public static String sharedResourcePath(final String resourceName) {
        return "/net/orpiske/maestro/results/main/action/report/" + resourceName;
    }
}
