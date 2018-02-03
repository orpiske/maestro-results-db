package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.mpt.reports.AbstractRenderer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
        super.copyResources(path, sharedResourcePath("sorttable.js"), "sorttable.js");
        super.copyResources(path, sharedResourcePath("favicon.png"), "favicon.png");

        FileUtils.copyDirectory(new File("template"), new File(path, "template"));
    }
}
