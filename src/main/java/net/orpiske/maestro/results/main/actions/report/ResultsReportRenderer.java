package net.orpiske.maestro.results.main.actions.report;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import net.orpiske.mpt.reports.AbstractRenderer;
import org.apache.commons.io.FileUtils;

import static net.orpiske.maestro.results.main.actions.report.RenderUtils.*;


public class ResultsReportRenderer extends AbstractRenderer {
    private String templateName;

    public ResultsReportRenderer(final String templateName, final Map<String, Object> context) {
        super(context);

        this.templateName = templateName;
    }


    @Override
    public String render() throws Exception {
        String path = templatedResourcePath(templateName,"index-results.html");
        return super.render(path);
    }
}
