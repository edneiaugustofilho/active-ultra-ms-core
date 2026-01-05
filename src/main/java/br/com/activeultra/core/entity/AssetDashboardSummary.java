package br.com.activeultra.core.asset.dashboard;

import br.com.activeultra.core.entity.TenantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "tb_asset_dashboard_summary")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AssetDashboardSummary extends TenantEntity {

    @Column(name = "total_assets", nullable = false)
    private Long totalAssets;

    @Column(
            name = "total_acquisition_value",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal totalAcquisitionValue;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "by_status", nullable = false, columnDefinition = "jsonb")
    private Map<String, Long> byStatus;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "by_category", nullable = false, columnDefinition = "jsonb")
    private Map<String, Long> byCategory;
}
