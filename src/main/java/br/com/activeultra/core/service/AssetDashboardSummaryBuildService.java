package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.AssetDashboardSummary;
import br.com.activeultra.core.entity.AssetDashboardSummaryByCategory;
import br.com.activeultra.core.entity.AssetDashboardSummaryByStatus;
import br.com.activeultra.core.gateway.dto.AssetByCategoryDto;
import br.com.activeultra.core.gateway.dto.AssetByStatusDto;
import br.com.activeultra.core.repository.AssetDashboardSummaryByCategoryRepository;
import br.com.activeultra.core.repository.AssetDashboardSummaryByStatusRepository;
import br.com.activeultra.core.repository.AssetDashboardSummaryRepository;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AssetDashboardSummaryBuildService {

    private final TenantContext tenantContext;
    private final AssetDashboardSummaryRepository summaryRepository;
    private final AssetRepository assetRepository;
    private final AssetDashboardSummaryByStatusRepository summaryByStatusRepository;
    private final AssetDashboardSummaryByCategoryRepository summaryByCategoryRepository;

    public AssetDashboardSummaryBuildService(TenantContext tenantContext,
                                             AssetDashboardSummaryRepository summaryRepository,
                                             AssetRepository assetRepository,
                                             AssetDashboardSummaryByStatusRepository summaryByStatusRepository,
                                             AssetDashboardSummaryByCategoryRepository summaryByCategoryRepository) {
        this.tenantContext = tenantContext;
        this.summaryRepository = summaryRepository;
        this.assetRepository = assetRepository;
        this.summaryByStatusRepository = summaryByStatusRepository;
        this.summaryByCategoryRepository = summaryByCategoryRepository;
    }

    public AssetDashboardSummary execute() {
        if (tenantContext.getTenantId().isEmpty()) {
            throw new IllegalArgumentException("Tenant ID is required");
        }

        final UUID tenantId = tenantContext.getTenantId().get();

        final Long totalAssets = assetRepository.countByTenantId(tenantId);
        final BigDecimal totalAcquisitionValue = assetRepository.sumAcquisitionValueByTenantid(tenantId);
        final List<AssetByStatusDto> byStatusListMap = assetRepository.countByStatus(tenantId);
        final List<AssetByCategoryDto> byCategoryListMap = assetRepository.countByCategory(tenantId);

        final AssetDashboardSummary summary = AssetDashboardSummary.builder()
                .tenantId(tenantId)
                .totalAssets(totalAssets)
                .byStatus(buildByStatus(byStatusListMap))
                .byCategory(buildByCategory(byCategoryListMap))
                .totalAcquisitionValue(totalAcquisitionValue)
                .build();

        summaryRepository.save(summary);
        
        summary.getByStatus()
                .forEach(summaryByStatus -> {
                    summaryByStatus.setSummary(summary);
                    summaryByStatusRepository.save(summaryByStatus);
                });
        summary.getByCategory()
                .forEach(summaryByCategory -> {
                    summaryByCategory.setSummary(summary);
                    summaryByCategoryRepository.save(summaryByCategory);
                });

        return summary;
    }

    private List<AssetDashboardSummaryByStatus> buildByStatus(List<AssetByStatusDto> byStatusListMap) {
        final List<AssetDashboardSummaryByStatus> byStatusList = new ArrayList<>();
        for (AssetByStatusDto assetByStatusDto : byStatusListMap) {
            byStatusList.add(buildByStatus(assetByStatusDto));
        }
        return byStatusList;
    }

    private AssetDashboardSummaryByStatus buildByStatus(AssetByStatusDto assetByStatusDto) {
        return AssetDashboardSummaryByStatus.builder()
                .status(assetByStatusDto.getStatus())
                .totalAssets(assetByStatusDto.getTotal())
                .totalAcquisitionValue(assetByStatusDto.getTotalAcquisitionValue())
                .build();
    }

    private List<AssetDashboardSummaryByCategory> buildByCategory(List<AssetByCategoryDto> byCategoryListMap) {
        final List<AssetDashboardSummaryByCategory> byCategoryList = new ArrayList<>();
        for (AssetByCategoryDto assetByCategoryDto : byCategoryListMap) {
            byCategoryList.add(buildByCategory(assetByCategoryDto));
        }
        return byCategoryList;
    }

    private AssetDashboardSummaryByCategory buildByCategory(AssetByCategoryDto assetByCategoryDto) {
        return AssetDashboardSummaryByCategory.builder()
                .category(assetByCategoryDto.getCategory())
                .totalAssets(assetByCategoryDto.getTotal())
                .totalAcquisitionValue(assetByCategoryDto.getTotalAcquisitionValue())
                .build();
    }
}
