package org.maestro.results.main.actions.data;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.Test;
import org.maestro.contrib.utils.Downloader;
import org.apache.commons.io.FileUtils;
import org.maestro.contrib.utils.resource.exceptions.ResourceExchangeException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataMover {
    private TestDao dao = new TestDao();

    private String downloadPath;
    private String tmpPath;
    private boolean dynamicNaming;

    public DataMover() {}


    private String getTestPath(final Test test) {
        return test.getTestName() + "/id/" + test.getTestId() + "/number/" + test.getTestNumber();
    }

    private void updateRecord(final Test test, final String from, final String to) {
        String oldUrl = test.getTestReportLink();
        String newUrl;

        if (dynamicNaming) {
            newUrl = to + (to.charAt(to.length() - 1) == '/' ? "" : "/") + getTestPath(test);
        }
        else {
             newUrl = oldUrl.replace(from, to);
        }

        System.err.println("Moving record: " + test.getTestId() + "/" + test.getTestNumber() + " from " + oldUrl + " to " + newUrl);

        if (downloadPath != null) {
            final String downloadUrl = test.getTestReportLink() + (downloadPath.charAt(0) == '/' ? "" : "/") + downloadPath;

            System.err.println("Downloading records from " + downloadUrl);
            if (!downloadReports(test, downloadUrl)) {
                return;
            }
        }

        test.setTestReportLink(newUrl);
        dao.update(test);
    }

    private boolean downloadReports(Test test, String oldUrl) {
        String path = tmpPath;

        if (tmpPath == null) {
            path = FileUtils.getTempDirectoryPath();
        }

        path = path + File.separator + "maestro" + File.separator + getTestPath(test);

        try {
            FileUtils.forceMkdirParent(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            Downloader.download(oldUrl, path, false);
        } catch (ResourceExchangeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setDynamicNaming(boolean dynamicNaming) {
        this.dynamicNaming = dynamicNaming;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    public void move(final String from, final String to) {
        List<Test> tests = dao.fetch();

        tests.parallelStream().forEach(record -> updateRecord(record, from, to));
    }


    public void move(final String from, final String to, final String testName) {
        List<Test> tests = dao.fetch(testName);

        tests.parallelStream().forEach(record -> updateRecord(record, from, to));
    }

    public void move(final String from, final String to, int initialId, int finalId, final String testName) {
        List<Test> tests = dao.fetch(initialId, finalId, testName);

        tests.parallelStream().forEach(record -> updateRecord(record, from, to));
    }
}
