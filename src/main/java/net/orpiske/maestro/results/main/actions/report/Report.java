package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.SutDao;
import net.orpiske.maestro.results.dto.Sut;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Report {

    private String outputDir;

    private List<ReportInfo> protocolReportsList = new LinkedList<>();
    private List<ReportInfo> contendedReportsList = new LinkedList<>();
    private List<ReportInfo> destinationScalabilityReportsList = new LinkedList<>();

    public Report(final String outputDir) {
        this.outputDir = outputDir;
    }

    void createReport() {
        SutDao sutDao = new SutDao();

        List<Sut> sutList = sutDao.fetchDistinct();
        sutList.forEach(this::createReportForSut);

        System.out.println("Number of reports created: " + protocolReportsList.size());

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("protocolReportsList", protocolReportsList);
        context.put("contendedReportsList", contendedReportsList);
        context.put("destinationScalabilityReportsList", destinationScalabilityReportsList);

        IndexRenderer indexRenderer = new IndexRenderer(ReportTemplates.DEFAULT, context);

        File indexFile = new File(outputDir, "index.html");
        try {
            FileUtils.writeStringToFile(indexFile, indexRenderer.render(), Charsets.UTF_8);

            indexRenderer.copyResources(indexFile.getParentFile());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void createReportForSut(final Sut sut) {
        boolean durableFlags[] = {true, false};
        int limitDestinations[] = {1, 10, 100};
        int messageSizes[] = {1, 10, 100};
        int connectionCounts[] = {1, 10, 100};


        final ProtocolReportCreator protocolReportCreator = new ProtocolReportCreator(outputDir);


        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (int connectionCount : connectionCounts) {
                    for (int limitDestination : limitDestinations) {
                        if (limitDestination > connectionCount) {
                            continue;
                        }

                        ReportInfo reportInfo = null;
                        try {
                            reportInfo = protocolReportCreator.create(sut, durable, limitDestination, messageSize, connectionCount);
                            if (reportInfo != null) {
                                protocolReportsList.add(reportInfo);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


        String protocols[] = { "AMQP", "ARTEMIS", "OPENWIRE" };

        ContendedReportCreator contendedReportCreator = new ContendedReportCreator(outputDir);
        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (String protocol : protocols) {

                    ReportInfo reportInfo = null;
                    try {
                        reportInfo = contendedReportCreator.create(sut, protocol, durable, messageSize);
                        if (reportInfo != null) {
                            contendedReportsList.add(reportInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        }


        DestinationScalabilityReportCreator destinationScalabilityReportCreator = new DestinationScalabilityReportCreator(outputDir);
        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (String protocol : protocols) {

                    ReportInfo reportInfo = null;
                    try {
                        reportInfo = destinationScalabilityReportCreator.create(sut, protocol, durable, messageSize);
                        if (reportInfo != null) {
                            destinationScalabilityReportsList.add(reportInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}