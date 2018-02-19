package net.orpiske.maestro.results.main.actions.report;

import java.util.Map;

import net.orpiske.mpt.reports.AbstractRenderer;

import static net.orpiske.maestro.results.main.actions.report.RenderUtils.*;


public class ReportRenderer extends AbstractRenderer {
    private final String templateName;
    private final String resourceName;

    public ReportRenderer(final String templateName, final String resourceName, final Map<String, Object> context) {
        super(context);

        this.templateName = templateName;
        this.resourceName = resourceName;
    }

    @Override
    public String render() throws Exception {
        String path = templatedResourcePath(templateName,resourceName);
        return super.render(path);
    }


}
