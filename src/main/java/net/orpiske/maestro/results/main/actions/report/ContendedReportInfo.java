package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.Sut;

public class ContendedReportInfo implements ReportInfo {
    private Sut sut;
    private String protocol;
    private String linkName;
    private boolean durable;
    private int limitDestinations;
    private int messageSize;
    private int connectionCount;

    public ContendedReportInfo(final Sut sut, final String protocol, boolean durable, int limitDestinations, int messageSize, int connectionCount) {
        this.sut = sut;
        this.protocol = protocol;
        this.durable = durable;
        this.limitDestinations = limitDestinations;
        this.messageSize = messageSize;
        this.connectionCount = connectionCount;

        this.linkName = baseName();
    }

    public Sut getSut() {
        return sut;
    }

    public String getLinkName() {
        return linkName;
    }

    public boolean isDurable() {
        return durable;
    }

    public int getLimitDestinations() {
        return limitDestinations;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public String getProtocol() {
        return protocol;
    }

    public String baseName() {
        return "report-contended-" + sut.getSutName() + "-" + sut.getSutVersion() + "-" + protocol + (durable ? "-" : "-non-") +
                "durable-" + "s" + messageSize;
    }
}
