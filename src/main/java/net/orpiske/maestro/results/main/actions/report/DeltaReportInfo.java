package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.Sut;

public class DeltaReportInfo implements ReportInfo {
    private final Sut sut;
    private final String protocol;
    private final int messageSize;
    private final String linkName;
    private long reportSize;

    public DeltaReportInfo(Sut sut, String protocol, int messageSize) {
        this.sut = sut;
        this.protocol = protocol;
        this.messageSize = messageSize;

        this.linkName = baseName();
    }

    public Sut getSut() {
        return sut;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public String getLinkName() {
        return linkName;
    }

    public long getReportSize() {
        return reportSize;
    }

    public void setReportSize(long reportSize) {
        this.reportSize = reportSize;
    }

    @Override
    public String baseName() {
        return "report-delta-" + sut.getSutName() + "-" + sut.getSutVersion() + "-" + protocol + "-s" + messageSize;
    }
}
