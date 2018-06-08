package net.orpiske.maestro.results.main.actions.report.custom;

import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.lib.filter.Filter;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.maestro.common.ConfigurationWrapper;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;

public class SysRecord implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SysRecord.class);
    private AbstractConfiguration config = ConfigurationWrapper.getConfig();

    @Override
    public Object filter(Object var, JinjavaInterpreter jinjavaInterpreter, String... strings) {
        TestResultRecord resultRecord = (TestResultRecord) var;
        logger.trace("Processing filter argument for {} with arg len {}", resultRecord,
                strings.length);

        String urlFormat = config.getString("sysinfo.record.urlformat");
        if (urlFormat == null) {
            return "#";
        }

        Instant instant = resultRecord.getTestDate().toInstant();
        LocalDateTime endTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime startTime = endTime.minusMinutes(30);

        OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
        ZoneOffset zoneOffset = odt.getOffset();

        logger.trace("Using zone offset {}", zoneOffset);

        String ret = urlFormat.replace("%HOSTNAME%", resultRecord.getTestHost())
                .replace("%START_TIME%", Long.toString(startTime.toInstant(zoneOffset).toEpochMilli()))
                .replace("%END_TIME%", Long.toString(endTime.toInstant(zoneOffset).toEpochMilli()));

        return ret;
    }

    @Override
    public String getName() {
        return "sysRecord";
    }
}
