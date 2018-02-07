package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.mpt.reports.AbstractRenderer;

import java.util.Map;

import static net.orpiske.maestro.results.main.actions.report.RenderUtils.templatedResourcePath;


public class ContendedReportRenderer extends AbstractRenderer {
    private String templateName;

    public ContendedReportRenderer(final String templateName, final Map<String, Object> context) {
        super(context);

        this.templateName = templateName;
    }


    @Override
    public String render() throws Exception {
        String path = templatedResourcePath(templateName,"contended-results.html");
        return super.render(path);
    }
}
