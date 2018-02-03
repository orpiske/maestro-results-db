package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.mpt.reports.AbstractRenderer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static net.orpiske.maestro.results.main.actions.report.RenderUtils.*;

public class IndexRenderer extends AbstractRenderer {
    private String templateName;

    public IndexRenderer(final String templateName, final Map<String, Object> context) {
        super(context);

        this.templateName = templateName;
    }




    @Override
    public String render() throws Exception {
        return super.render(templatedResourcePath(templateName,"index-reports.html"));
    }

    public void copyResources(File path) throws IOException {
        super.copyResources(path, sharedResourcePath("favicon.png"), "favicon.png");

        URL resourcesUrl = this.getClass().getResource("/net/orpiske/maestro/results/main/action/report/" + templateName + "/resources");

        File resources = new File(resourcesUrl.getPath());

        FileUtils.copyDirectory(resources, new File(path, "resources"));
    }
}
