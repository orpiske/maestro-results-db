package org.maestro.results.server.collector;

import org.maestro.common.Constants;
import org.maestro.common.client.notes.SutDetails;
import org.maestro.common.client.notes.TestExecutionInfo;
import org.maestro.reports.dto.Report;
import org.maestro.reports.server.collector.PostAggregationHook;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;
import org.maestro.results.dto.Test;
import org.maestro.results.exceptions.DataNotFoundException;
import org.maestro.results.loader.ReportLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InsertToResultsDBHook implements PostAggregationHook {
    private static final Logger logger = LoggerFactory.getLogger(InsertToResultsDBHook.class);
    private SutDao sutDao = new SutDao();

    /*
    private int testId;
    private int testNumber;
    private String testName;
    private String testResult;
    private int testParameterId;
    private int sutId;
    private String testReportLink;
    private String testDataStorageInfo;
    private String testTags;
    private Date testDate;
    private int testDuration;
    private String testDurationType;
    private int testTargetRate;
    private String maestroVersion = Constants.VERSION;
     */

    private Test convertReportToTest(final Report report) {
        Test test = new Test();

        test.setTestId(report.getTestId());
        test.setTestNumber(report.getTestNumber());
        test.setTestName(report.getTestName());
        test.setTestResult(report.getTestResult());
//        ????
//        test.setSutId();
        // Not needed
        test.setTestReportLink("");
        test.setTestDataStorageInfo("");

        test.setTestTags(report.getTestDescription());
        test.setTestDate(report.getTestDate());
//        Read from the properties file
//        test.setTestDuration(report.get);
//        test.setTestDurationType()
//        test.setTestTargetRate();
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
                sut = sutDao.fetch(sutDetails.getSutName(), sutDetails.getSutVersion());
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

        ReportLoader loader = new ReportLoader(convertReportToTest(first), sutDetails.getLabName());

//        testExecutionInfo.getTest().

//        for (Report report : list) {
//            logger.info("Running the post aggregation for {}", report);
//        }
    }
}
