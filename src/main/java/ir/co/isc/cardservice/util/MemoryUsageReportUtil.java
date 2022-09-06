package ir.co.isc.cardservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MemoryUsageReportUtil {

    private final Logger logger = LoggerFactory.getLogger(MemoryUsageReportUtil.class);

    @Scheduled(cron = "0 0/1 1/1 1/1 * ? ")
    public void reportCurrentTime() {
        logger.info("The Memory Usage Is : " +
                String.valueOf(Runtime.getRuntime().totalMemory() / Math.pow(2,10)) +
                " MB");
        System.out.println("The Memory Usage Is : " +
                String.valueOf(Runtime.getRuntime().totalMemory() / Math.pow(2,10)) +
                " MB");
    }
}
