package br.com.activeultra.core.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AssetDashboardSummarySchedule {


    @Scheduled(cron = "0 0 8-18 * * *")
    public void createDashboardSumary() {

    }

}
