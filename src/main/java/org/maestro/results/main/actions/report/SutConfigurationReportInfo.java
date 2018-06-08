package org.maestro.results.main.actions.report;

import org.maestro.results.dto.Sut;

public class SutConfigurationReportInfo implements ReportInfo {
    private final Sut sut;
    private final String protocol;
    private final String configuration;
    private final String linkName;
    private final boolean durable;
    private final int limitDestinations;
    private final int messageSize;
    private final int connectionCount;

    public SutConfigurationReportInfo(final Sut sut, final String protocol, final String configuration, boolean durable, int limitDestinations, int messageSize, int connectionCount) {
        this.sut = sut;
        this.protocol = protocol;
        this.durable = durable;
        this.limitDestinations = limitDestinations;
        this.messageSize = messageSize;
        this.connectionCount = connectionCount;
        this.configuration = configuration;

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

    public String getSanitizedConfiguration() {
        return configuration.replace(",", "-").replace("+", "-with-");
    }

    public String getConfiguration() {
        return configuration;
    }

    public String baseName() {
        return "report-" + sut.getSutName() + "-" + sut.getSutVersion() + (durable ? "-" : "-non-") + "durable-ld" +
                limitDestinations + "-s" + messageSize + "-c" + connectionCount + "-" + protocol + "-"
                + getSanitizedConfiguration();
    }
}
