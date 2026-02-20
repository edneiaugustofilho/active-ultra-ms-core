package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.DashboardByStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DashboardByStatusRepository extends JpaRepository<DashboardByStatus, UUID> {

    @Query("SELECT a FROM DashboardByStatus a WHERE a.summary.id = :summaryId")
    Optional<List<DashboardByStatus>> findBySummaryId(@Param("summaryId") UUID summaryId);


}
