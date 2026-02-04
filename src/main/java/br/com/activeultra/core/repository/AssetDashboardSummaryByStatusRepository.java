package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.AssetDashboardSummaryByStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetDashboardSummaryByStatusRepository extends JpaRepository<AssetDashboardSummaryByStatus, UUID> {

    @Query("SELECT a FROM AssetDashboardSummaryByStatus a WHERE a.summary.id = :summaryId")
    Optional<List<AssetDashboardSummaryByStatus>> findBySummaryId(@Param("summaryId") UUID summaryId);


}
