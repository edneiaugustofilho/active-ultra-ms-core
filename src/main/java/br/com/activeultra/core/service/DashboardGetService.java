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
    private final DashboardRepository dashboardRepository;
    private final DashboardByStatusRepository dashboardByStatusRepository;
    private final DashboardByCategoryRepository dashboardByCategoryRepository;
    private final DashboardBuildService dashboardBuildService;

    public DashboardGetService(TenantContext tenantContext,
                               DashboardRepository dashboardRepository,
                               DashboardByStatusRepository dashboardByStatusRepository,
                               DashboardByCategoryRepository dashboardByCategoryRepository,
                               DashboardBuildService dashboardBuildService) {
        this.tenantContext = tenantContext;
        this.dashboardRepository = dashboardRepository;
        this.dashboardByStatusRepository = dashboardByStatusRepository;
        this.dashboardByCategoryRepository = dashboardByCategoryRepository;
        this.dashboardBuildService = dashboardBuildService;
    }


    public Dashboard execute() {
        if (tenantContext.getTenantId().isEmpty()) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        Optional<Dashboard> dashboardOptional = dashboardRepository.findLastByTenantId(tenantContext.getTenantId().get());

        if (dashboardOptional.isEmpty()) {
            dashboardBuildService.execute();
            dashboardOptional = dashboardRepository.findLastByTenantId(tenantContext.getTenantId().get());
        }

        if (dashboardOptional.isPresent()) {
            Dashboard dashboard = dashboardOptional.get();

            dashboard.setByStatus(dashboardByStatusRepository.findBySummaryId(dashboard.getId()).orElse(new ArrayList<>()));
            dashboard.setByCategory(dashboardByCategoryRepository.findBySummaryId(dashboard.getId()).orElse(new ArrayList<>()));

            return dashboard;
        }

        return new Dashboard();
    }


}
