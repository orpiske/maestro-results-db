package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.mpt.reports.AbstractRenderer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class IndexRenderer extends AbstractRenderer {

    public IndexRenderer(final Map<String, Object> context) {
        super(context);
    }


    @Override
    public String render() throws Exception {
        return super.render("/net/orpiske/maestro/results/main/action/report/index-reports.html");
    }

    public void copyResources(File path) throws IOException {
        super.copyResources(path, "/net/orpiske/mpt/reports/sorttable.js", "sorttable.js");
        super.copyResources(path, "/net/orpiske/mpt/reports/favicon.png", "favicon.png");

    }
}
