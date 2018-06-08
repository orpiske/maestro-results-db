package net.orpiske.maestro.results.main.actions.report;

import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import net.orpiske.maestro.results.main.actions.report.custom.SysRecord;
import org.maestro.reports.AbstractRenderer;

import static net.orpiske.maestro.results.main.actions.report.RenderUtils.*;


public class ReportRenderer extends AbstractRenderer {
    private final String templateName;
    private final String resourceName;

    public ReportRenderer(final String templateName, final String resourceName) {
        super();

        this.templateName = templateName;
        this.resourceName = resourceName;

        Jinjava jinjava = getJinjava();

        jinjava.getGlobalContext().registerFilter(new SysRecord());
    }

    @Override
    public String render(final Map<String, Object> context) throws Exception {
        String path = templatedResourcePath(templateName,resourceName);
        return super.render(path, context);
    }


}
