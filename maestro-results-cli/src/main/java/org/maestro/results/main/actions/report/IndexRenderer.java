package org.maestro.results.main.actions.report;

import org.maestro.reports.AbstractRenderer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.maestro.results.main.actions.report.RenderUtils.*;

public class IndexRenderer extends AbstractRenderer {
    private final String templateName;

    public IndexRenderer(final String templateName) {
        super();

        this.templateName = templateName;
    }

    @Override
    public String render(Map<String, Object> context) throws Exception {
        return super.render(templatedResourcePath(templateName,"index-reports.html"), context);
    }

    protected void copyResources(File path) throws IOException {
        super.copyResources(path, sharedResourcePath("favicon.png"), "favicon.png");

        // Template resources
        URL resourcesUrl = this.getClass().getResource("/org/maestro/results/main/action/report/" + templateName + "/resources");

        File resources = new File(resourcesUrl.getPath());

        FileUtils.copyDirectory(resources, new File(path, "resources"));

        // Copy template resources
        URL staticUrl = this.getClass().getResource("/org/maestro/results/main/action/report/" + templateName + "/static");

        File staticFiles = new File(staticUrl.getPath());

        FileUtils.copyDirectory(staticFiles, new File(path, "static"));
    }
}
