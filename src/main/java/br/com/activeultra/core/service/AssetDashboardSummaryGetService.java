package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.AssetDashboardSummary;
import br.com.activeultra.core.entity.AssetDashboardSummaryByCategory;
import br.com.activeultra.core.repository.AssetDashboardSummaryByCategoryRepository;
import br.com.activeultra.core.repository.AssetDashboardSummaryByStatusRepository;
import br.com.activeultra.core.repository.AssetDashboardSummaryRepository;
import br.com.activeultra.core.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssetDashboardSummaryGetService {

    private final TenantContext tenantContext;
    private final AssetDashboardSummaryRepository summaryRepository;
    private final AssetDashboardSummaryByStatusRepository summaryByStatusRepository;
    private final AssetDashboardSummaryByCategoryRepository summaryByCategoryRepository;


    public AssetDashboardSummaryGetService(TenantContext tenantContext,
                                           AssetDashboardSummaryRepository summaryRepository,
                                           AssetDashboardSummaryByStatusRepository summaryByStatusRepository,
                                           AssetDashboardSummaryByCategoryRepository summaryByCategoryRepository) {
        this.tenantContext = tenantContext;
        this.summaryRepository = summaryRepository;
        this.summaryByStatusRepository = summaryByStatusRepository;
        this.summaryByCategoryRepository = summaryByCategoryRepository;
    }


    public AssetDashboardSummary execute() {
        if (tenantContext.getTenantId().isEmpty()) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        AssetDashboardSummary summary = summaryRepository.findLastByTenantId(tenantContext.getTenantId().get());
        summary.setByStatus(summaryByStatusRepository.findBySummaryId(summary.getId()).orElse(new ArrayList<>()));
        summary.setByCategory(summaryByCategoryRepository.findBySummaryId(summary.getId()).orElse(new ArrayList<>()));

        return summary;
    }


}
