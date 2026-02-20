package br.com.activeultra.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_dashboard")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard extends TenantEntity {

    @Column(name = "total_assets", nullable = false)
    private Long totalAssets;

    @Column(
            name = "total_acquisition_value",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal totalAcquisitionValue;

    @OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DashboardByStatus> byStatus = new ArrayList<>();

    @OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DashboardByCategory> byCategory = new ArrayList<>();
}
