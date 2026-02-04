package br.com.activeultra.core.entity;

import br.com.activeultra.core.enums.AssetCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_asset_dashboard_summary_by_category")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AssetDashboardSummaryByCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private AssetCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summary_id", referencedColumnName = "id")
    private AssetDashboardSummary summary;

    @Column(name = "total_assets", nullable = false)
    private Long totalAssets;

    @Column(name = "total_acquisition_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAcquisitionValue;
}
