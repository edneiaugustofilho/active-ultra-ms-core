package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Dashboard;
import br.com.activeultra.core.entity.DashboardByCategory;
import br.com.activeultra.core.entity.DashboardByStatus;
import br.com.activeultra.core.gateway.dto.AssetByCategoryDto;
import br.com.activeultra.core.gateway.dto.AssetByStatusDto;
import br.com.activeultra.core.repository.DashboardByCategoryRepository;
import br.com.activeultra.core.repository.DashboardByStatusRepository;
import br.com.activeultra.core.repository.DashboardRepository;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardBuildService {

    private final TenantContext tenantContext;
    private final DashboardRepository dashboardRepository;
    private final AssetRepository assetRepository;
    private final DashboardByStatusRepository dashboardByStatusRepository;
    private final DashboardByCategoryRepository dashboardByCategoryRepository;

    public DashboardBuildService(TenantContext tenantContext,
                                 DashboardRepository dashboardRepository,
                                 AssetRepository assetRepository,
                                 DashboardByStatusRepository dashboardByStatusRepository,
                                 DashboardByCategoryRepository dashboardByCategoryRepository) {
        this.tenantContext = tenantContext;
        this.dashboardRepository = dashboardRepository;
        this.assetRepository = assetRepository;
        this.dashboardByStatusRepository = dashboardByStatusRepository;
        this.dashboardByCategoryRepository = dashboardByCategoryRepository;
    }

    public Dashboard execute() {
        if (tenantContext.getTenantId().isEmpty()) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        final UUID tenantId = tenantContext.getTenantId().get();

        final Long totalAssets = assetRepository.countByTenantId(tenantId);
        final BigDecimal totalAcquisitionValue = assetRepository.sumAcquisitionValueByTenantid(tenantId);
        final List<AssetByStatusDto> byStatusListMap = assetRepository.countByStatus(tenantId);
        final List<AssetByCategoryDto> byCategoryListMap = assetRepository.countByCategory(tenantId);

        final Dashboard summary = Dashboard.builder()
                .tenantId(tenantId)
                .totalAssets(totalAssets)
                .byStatus(buildByStatus(byStatusListMap))
                .byCategory(buildByCategory(byCategoryListMap))
                .totalAcquisitionValue(totalAcquisitionValue)
                .build();

        dashboardRepository.save(summary);
        
        summary.getByStatus()
                .forEach(summaryByStatus -> {
                    summaryByStatus.setSummary(summary);
                    dashboardByStatusRepository.save(summaryByStatus);
                });
        summary.getByCategory()
                .forEach(summaryByCategory -> {
                    summaryByCategory.setSummary(summary);
                    dashboardByCategoryRepository.save(summaryByCategory);
                });

        return summary;
    }

    private List<DashboardByStatus> buildByStatus(List<AssetByStatusDto> byStatusListMap) {
        final List<DashboardByStatus> byStatusList = new ArrayList<>();
        for (AssetByStatusDto assetByStatusDto : byStatusListMap) {
            byStatusList.add(buildByStatus(assetByStatusDto));
        }
        return byStatusList;
    }

    private DashboardByStatus buildByStatus(AssetByStatusDto assetByStatusDto) {
        return DashboardByStatus.builder()
                .status(assetByStatusDto.getStatus())
                .totalAssets(assetByStatusDto.getTotal())
                .totalAcquisitionValue(assetByStatusDto.getTotalAcquisitionValue())
                .build();
    }

    private List<DashboardByCategory> buildByCategory(List<AssetByCategoryDto> byCategoryListMap) {
        final List<DashboardByCategory> byCategoryList = new ArrayList<>();
        for (AssetByCategoryDto assetByCategoryDto : byCategoryListMap) {
            byCategoryList.add(buildByCategory(assetByCategoryDto));
        }
        return byCategoryList;
    }

    private DashboardByCategory buildByCategory(AssetByCategoryDto assetByCategoryDto) {
        return DashboardByCategory.builder()
                .category(assetByCategoryDto.getCategory())
                .totalAssets(assetByCategoryDto.getTotal())
                .totalAcquisitionValue(assetByCategoryDto.getTotalAcquisitionValue())
                .build();
    }
}
