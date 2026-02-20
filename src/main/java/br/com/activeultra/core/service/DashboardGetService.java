package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Dashboard;
import br.com.activeultra.core.repository.DashboardByCategoryRepository;
import br.com.activeultra.core.repository.DashboardByStatusRepository;
import br.com.activeultra.core.repository.DashboardRepository;
import br.com.activeultra.core.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DashboardGetService {

    private final TenantContext tenantContext;
    private final DashboardRepository summaryRepository;
    private final DashboardByStatusRepository summaryByStatusRepository;
    private final DashboardByCategoryRepository summaryByCategoryRepository;


    public DashboardGetService(TenantContext tenantContext,
                               DashboardRepository summaryRepository,
                               DashboardByStatusRepository summaryByStatusRepository,
                               DashboardByCategoryRepository summaryByCategoryRepository) {
        this.tenantContext = tenantContext;
        this.summaryRepository = summaryRepository;
        this.summaryByStatusRepository = summaryByStatusRepository;
        this.summaryByCategoryRepository = summaryByCategoryRepository;
    }


    public Dashboard execute() {
        if (tenantContext.getTenantId().isEmpty()) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        Optional<Dashboard> dashboardOptional = summaryRepository.findLastByTenantId(tenantContext.getTenantId().get());
        if (dashboardOptional.isPresent()) {
            Dashboard dashboard = dashboardOptional.get();

            dashboard.setByStatus(summaryByStatusRepository.findBySummaryId(dashboard.getId()).orElse(new ArrayList<>()));
            dashboard.setByCategory(summaryByCategoryRepository.findBySummaryId(dashboard.getId()).orElse(new ArrayList<>()));

            return dashboard;
        }

        return new Dashboard();
    }


}
