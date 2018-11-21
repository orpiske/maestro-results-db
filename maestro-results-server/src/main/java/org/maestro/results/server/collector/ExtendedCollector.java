package org.maestro.results.server.collector;

import org.maestro.client.exchange.support.PeerInfo;
import org.maestro.reports.server.collector.DefaultReportsCollector;

import java.io.File;

public class ExtendedCollector extends DefaultReportsCollector {
    public ExtendedCollector(final String maestroURL, final PeerInfo peerInfo, final File dataDir) {
        super(maestroURL, peerInfo, dataDir, new ExtendedReportCollectorWorkerFactory());
    }
}
