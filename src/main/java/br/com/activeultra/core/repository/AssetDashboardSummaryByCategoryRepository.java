package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.AssetDashboardSummaryByCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetDashboardSummaryByCategoryRepository extends JpaRepository<AssetDashboardSummaryByCategory, UUID> {

    @Query("SELECT a FROM AssetDashboardSummaryByCategory a WHERE a.summary.id = :summaryId")
    Optional<List<AssetDashboardSummaryByCategory>> findBySummaryId(@Param("summaryId") UUID summaryId);

}
