package net.orpiske.maestro.results.main.actions.data;

import net.orpiske.maestro.results.dao.TestDao;
import net.orpiske.maestro.results.dto.Test;
import net.orpiske.mpt.utils.Downloader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipInputStream;

public class DataMover {
    private TestDao dao = new TestDao();

    private String downloadPath;
    private String tmpPath;
    private boolean dynamicNaming;

    public DataMover() {}

    protected void downloadReport(final String downloadUrl, final String path) {
        try {
            Downloader.download(downloadUrl, path, false);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
        }
    }

    private String getTestPath(final Test test) {
        return test.getTestName() + "/" + test.getTestId() + "/" + test.getTestNumber();
    }

    private void updateRecord(final Test test, final String from, final String to) {
        String oldUrl = test.getTestReportLink();
        String newUrl;

        if (downloadPath != null) {
            oldUrl = oldUrl + (downloadPath.charAt(0) == '/' ? "" : "/") + downloadPath;
        }

        if (dynamicNaming) {
            newUrl = to + (to.charAt(to.length() - 1) == '/' ? "" : "/") + getTestPath(test);
        }
        else {
             newUrl = oldUrl.replace(from, to);
        }

        System.err.println("Moving record: " + test.getTestId() + "/" + test.getTestNumber() + " from " + oldUrl + " to " + newUrl);

        if (downloadPath != null) {
            String path = tmpPath;

            if (tmpPath == null) {
                path = FileUtils.getTempDirectoryPath();
            }

            path = path + File.separator + "maestro" + File.separator + getTestPath(test);

            try {
                FileUtils.forceMkdirParent(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            downloadReport(oldUrl, path);
        }
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
}
