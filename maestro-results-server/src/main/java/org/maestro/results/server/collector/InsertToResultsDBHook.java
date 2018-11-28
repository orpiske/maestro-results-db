package org.maestro.results.server.collector;

import org.maestro.common.Constants;
import org.maestro.common.client.notes.SutDetails;
import org.maestro.common.client.notes.TestExecutionInfo;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dto.Report;
import org.maestro.reports.server.collector.PostAggregationHook;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;
import org.maestro.results.dto.Test;
import org.maestro.results.loader.ReportLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class InsertToResultsDBHook implements PostAggregationHook {
    private static final Logger logger = LoggerFactory.getLogger(InsertToResultsDBHook.class);
    private SutDao sutDao = new SutDao();

    /*
     Converts a report to a test. Skipped items are read from the properties file:
     test duration, duration type and target rate.
     */
    private Test convertReportToTest(final Report report, final Sut sut) {
        Test test = new Test();

        test.setTestId(report.getTestId());
        test.setTestNumber(report.getTestNumber());
        test.setTestName(report.getTestName());
        test.setTestResult(report.getTestResult());
        test.setSutId(sut.getSutId());

        // Not needed
        test.setTestReportLink("");
        test.setTestDataStorageInfo("");

        test.setTestTags(report.getTestDescription());
        test.setTestDate(report.getTestDate());

        test.setMaestroVersion(Constants.VERSION);

        return test;
    }


    private Sut getSutFromDetails(final SutDetails sutDetails) {
        Sut sut;

        try {
            if (sutDetails.getSutId() != SutDetails.UNSPECIFIED) {
                sut = sutDao.fetchById(sutDetails.getSutId());
                logger.info("Located a sut record for the requested SUT ID {}", sutDetails.getSutId());
            } else {
                sut = sutDao.fetch(sutDetails.getSutName(), sutDetails.getSutVersion(), sutDetails.getSutTags());
                logger.info("Located a sut record for {} version {}", sutDetails.getSutName(),
                        sutDetails.getSutVersion());
            }
        }
        catch (DataNotFoundException e) {
            sut = new Sut();

            sut.setSutName(sutDetails.getSutName());
            sut.setSutVersion(sutDetails.getSutVersion());
            sut.setSutJvmInfo(sutDetails.getSutJvmVersion());
            sut.setSutOther(sutDetails.getSutOtherInfo());
            sut.setSutTags(sutDetails.getSutTags());

            int id;

            if (sutDetails.getSutId() != SutDetails.UNSPECIFIED) {
                logger.warn("A SUT record for ID {} was not found on the DB and will be inserted", sutDetails.getSutId());
                sut.setSutId(sut.getSutId());

                id = sutDao.insertWithId(sut);
            }
            else {
                logger.warn("A SUT record for {} version {} was not found on the DB and will be inserted", sutDetails.getSutName(),
                        sutDetails.getSutName());

                id = sutDao.insert(sut);
            }

            sut.setSutId(id);
            logger.info("A new SUT record was added with ID {}", id);
        }

        return sut;
    }

    @Override
    public void exec(final TestExecutionInfo testExecutionInfo, final List<Report> list) {
        try {
            if (!testExecutionInfo.hasSutDetails()) {
                logger.warn("The test cannot be added to the results DB because the SUT details are missing");

                return;
            }

            SutDetails sutDetails = testExecutionInfo.getSutDetails();
            Sut sut = getSutFromDetails(sutDetails);

            /*
             Obs: uses the report information, because the Test object in the TestExecutionInfo does not
             contain the actual test ID and test number (it only contains the *requested* ones, but not
             the actual ones).
             */
            Report first = list.get(0);

            ReportLoader loader = new ReportLoader(convertReportToTest(first, sut), sutDetails.getLabName());

            loader.recordTest();

            for (Report report : list) {
                logger.info("Loading new test record for {}", report);

                loader.load(new File(report.getLocation()), report.getTestHost(), report.getTestHostRole());
            }
        }
        catch (Throwable t) {
            logger.error("Unable to insert data into the results DB: {}", t.getMessage(), t);
        }
    }
}
