package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dto.Sut;

class ReportInfo {
    private Sut sut;
    private String linkName;
    private boolean durable;
    private int limitDestinations;
    private int messageSize;
    private int connectionCount;

    public ReportInfo(Sut sut, String linkName, boolean durable, int limitDestinations, int messageSize, int connectionCount) {
        this.sut = sut;
        this.linkName = linkName;
        this.durable = durable;
        this.limitDestinations = limitDestinations;
        this.messageSize = messageSize;
        this.connectionCount = connectionCount;
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
}
