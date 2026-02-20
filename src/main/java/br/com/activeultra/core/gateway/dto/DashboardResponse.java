package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.entity.Dashboard;
import br.com.activeultra.core.entity.DashboardByCategory;
import br.com.activeultra.core.entity.DashboardByStatus;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DashboardResponse(
        UUID id,
        UUID tenantId,
        Long totalAssets,
        BigDecimal totalAcquisitionValue,
        List<ByStatus> byStatus,
        List<ByCategory> byCategory,
        LocalDateTime updatedAt
) {
    public record ByStatus(AssetStatus status, Long totalAssets, BigDecimal totalAcquisitionValue) {
        public static ByStatus fromEntity(DashboardByStatus summaryByStatus) {
            return new ByStatus(summaryByStatus.getStatus(), summaryByStatus.getTotalAssets(), summaryByStatus.getTotalAcquisitionValue());
        }
    }
    public record ByCategory(AssetCategory category, Long totalAssets, BigDecimal totalAcquisitionValue) {
        public static ByCategory fromEntity(DashboardByCategory summaryByCategory) {
            return new ByCategory(summaryByCategory.getCategory(), summaryByCategory.getTotalAssets(), summaryByCategory.getTotalAcquisitionValue());
        }
    }

    public static DashboardResponse fromEntity(Dashboard summary) {
        return new DashboardResponse(summary.getId(), summary.getTenantId(),
                summary.getTotalAssets(), summary.getTotalAcquisitionValue(),
                summary.getByStatus().stream().map(ByStatus::fromEntity).toList(),
                summary.getByCategory().stream().map(ByCategory::fromEntity).toList(),
                summary.getUpdatedAt());
    }
}