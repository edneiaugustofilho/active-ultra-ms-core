package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.DashboardByCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DashboardByCategoryRepository extends JpaRepository<DashboardByCategory, UUID> {

    @Query("SELECT a FROM DashboardByCategory a WHERE a.summary.id = :summaryId")
    Optional<List<DashboardByCategory>> findBySummaryId(@Param("summaryId") UUID summaryId);

}
