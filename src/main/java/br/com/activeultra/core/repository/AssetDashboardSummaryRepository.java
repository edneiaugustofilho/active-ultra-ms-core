package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.AssetDashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AssetDashboardSummaryRepository extends JpaRepository<AssetDashboardSummary, UUID> {

    @Query("SELECT a FROM AssetDashboardSummary a WHERE a.tenantId = :tenantId ORDER BY a.createdAt DESC LIMIT 1")
    AssetDashboardSummary findLastByTenantId(@Param("tenantId") UUID tenantId);

}
