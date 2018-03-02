package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.SutDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.main.actions.report.exceptions.EmptyResultSet;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Report {
    private static final Logger logger = LoggerFactory.getLogger(Report.class);
    private final String outputDir;

    private final List<ReportInfo> protocolReportsList = Collections.synchronizedList(new LinkedList<>());
    private final List<ReportInfo> contendedReportsList = Collections.synchronizedList(new LinkedList<>());
    private final List<ReportInfo> destinationScalabilityReportsList = Collections.synchronizedList(new LinkedList<>());
    private final List<ReportInfo> configurationReportList = Collections.synchronizedList(new LinkedList<>());
    private final List<ReportInfo> deltaReportsList = Collections.synchronizedList(new LinkedList<>());

    private final SutDao sutDao = new SutDao();

    public Report(final String outputDir) {
        this.outputDir = outputDir;
    }

    void createReport() {
        List<Sut> sutList = sutDao.fetchDistinct();
        sutList.parallelStream().forEach(this::createReportForSut);

        logger.debug("Number of reports created: {}", protocolReportsList.size());

        logger.info("Generating system health report");
        SystemHealthReportCreator systemHealthReportCreator = new SystemHealthReportCreator(outputDir);

        try {
            systemHealthReportCreator.create();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> context = new HashMap<>();
        context.put("protocolReportsList", protocolReportsList);
        context.put("contendedReportsList", contendedReportsList);
        context.put("destinationScalabilityReportsList", destinationScalabilityReportsList);
        context.put("configurationReportList", configurationReportList);
        context.put("deltaReportsList", deltaReportsList);


        logger.info("Generating report index");
        IndexRenderer indexRenderer = new IndexRenderer(ReportTemplates.DEFAULT, context);

        File indexFile = new File(outputDir, "index.html");
        try {
            FileUtils.writeStringToFile(indexFile, indexRenderer.render(), StandardCharsets.UTF_8);

            indexRenderer.copyResources(indexFile.getParentFile());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createReportForSut(final Sut sut) {
        boolean durableFlags[] = {true, false};
        int limitDestinations[] = {1, 10, 100};
        int messageSizes[] = {100, 1024, 10240};
        int connectionCounts[] = {1, 10, 100};
        String protocols[] = {"AMQP", "ARTEMIS", "OPENWIRE"};

        List<String> configurations = sutDao.fetchSutTags(sut.getSutName(), sut.getSutVersion());

        createProtocolReports(sut, protocols, durableFlags, limitDestinations, messageSizes, connectionCounts, configurations);

        createContendedReports(sut, protocols, durableFlags, messageSizes);

        createDestinationScalabilityReports(sut, protocols, durableFlags, messageSizes);

        createDeltaReports(sut, protocols, messageSizes);

    }

    private void createDeltaReports(final Sut sut, String[] protocols, int[] messageSizes) {
        DeltaReportCreator deltaReportCreator = new DeltaReportCreator(outputDir);

        for (String protocol : protocols) {
            for (int messageSize : messageSizes) {
                ReportInfo reportInfo;
                try {
                    reportInfo = deltaReportCreator.create(sut, protocol, messageSize);
                    if (reportInfo != null) {
                        deltaReportsList.add(reportInfo);
                    }
                } catch (EmptyResultSet e) {
                    logger.trace(e.getMessage());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    private void createDestinationScalabilityReports(Sut sut, String[] protocols, boolean[] durableFlags, int[] messageSizes) {
        DestinationScalabilityReportCreator destinationScalabilityReportCreator = new DestinationScalabilityReportCreator(outputDir);
        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (String protocol : protocols) {

                    ReportInfo reportInfo;
                    try {
                        reportInfo = destinationScalabilityReportCreator.create(sut, protocol, durable, messageSize);
                        if (reportInfo != null) {
                            destinationScalabilityReportsList.add(reportInfo);
                        }
                    } catch (EmptyResultSet e) {
                        logger.trace(e.getMessage());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private void createContendedReports(Sut sut, String[] protocols, boolean[] durableFlags, int[] messageSizes) {
        ContendedReportCreator contendedReportCreator = new ContendedReportCreator(outputDir);
        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (String protocol : protocols) {

                    ReportInfo reportInfo;
                    try {
                        reportInfo = contendedReportCreator.create(sut, protocol, durable, messageSize);
                        if (reportInfo != null) {
                            contendedReportsList.add(reportInfo);
                        }
                    } catch (EmptyResultSet e) {
                      logger.trace(e.getMessage());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private void createProtocolReports(Sut sut, String[] protocols, boolean[] durableFlags, int[] limitDestinations, int[] messageSizes, int[] connectionCounts, List<String> configurations) {
        final SutConfigurationReportCreator sutConfigurationReportCreator = new SutConfigurationReportCreator(outputDir);

        final ProtocolReportCreator protocolReportCreator = new ProtocolReportCreator(outputDir);
        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (int connectionCount : connectionCounts) {
                    for (int limitDestination : limitDestinations) {
                        if (limitDestination > connectionCount) {
                            continue;
                        }

                        ReportInfo reportInfo;
                        try {
                            reportInfo = protocolReportCreator.create(sut, durable, limitDestination, messageSize,
                                    connectionCount);
                            if (reportInfo != null) {
                                protocolReportsList.add(reportInfo);
                            }

                            for (String configuration : configurations) {
                                for (String protocol : protocols) {
                                    try {
                                        ReportInfo configReport = sutConfigurationReportCreator.create(sut, protocol,
                                                configuration, durable, limitDestination, messageSize, connectionCount);

                                        if (configReport != null) {
                                            configurationReportList.add(configReport);
                                        }
                                    } catch (EmptyResultSet e) {
                                        logger.trace(e.getMessage());
                                    } catch (Exception e) {
                                        logger.error(e.getMessage(), e);
                                    }
                                }
                            }
                        } catch (EmptyResultSet e) {
                            logger.trace(e.getMessage());
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }
}