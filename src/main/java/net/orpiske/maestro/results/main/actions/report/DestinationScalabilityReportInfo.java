package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.Sut;

public class DestinationScalabilityReportInfo implements ReportInfo {
    private final Sut sut;
    private final String protocol;
    private final String linkName;
    private final boolean durable;
    private final int messageSize;
    private final int connectionCount;

    public DestinationScalabilityReportInfo(final Sut sut, final String protocol, boolean durable, int messageSize, int connectionCount) {
        this.sut = sut;
        this.protocol = protocol;
        this.durable = durable;
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
        return "report-ds-" + sut.getSutName() + "-" + sut.getSutVersion() + "-" + protocol + (durable ? "-" : "-non-") +
                "durable-" + "s" + messageSize;
    }
}
