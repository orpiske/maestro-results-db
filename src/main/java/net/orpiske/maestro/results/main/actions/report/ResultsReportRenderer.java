package net.orpiske.maestro.results.main.actions.report;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.orpiske.mpt.reports.AbstractRenderer;

import static net.orpiske.maestro.results.main.actions.report.RenderUtils.*;


public class ResultsReportRenderer extends AbstractRenderer {
    private String templateName;

    public ResultsReportRenderer(final String templateName, final Map<String, Object> context) {
        super(context);

        this.templateName = templateName;
    }


    @Override
    public String render() throws Exception {
        return super.render(templatedResourcePath(templateName,"index-results.html"));
    }

    public void copyResources(File path) throws IOException {
        super.copyResources(path, sharedResourcePath("sorttable.js"), "sorttable.js");
        super.copyResources(path, sharedResourcePath("favicon.png"), "favicon.png");

    }
}
