package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DashboardRepository extends JpaRepository<Dashboard, UUID> {

    @Query("SELECT a FROM Dashboard a WHERE a.tenantId = :tenantId ORDER BY a.createdAt DESC LIMIT 1")
    Optional<Dashboard> findLastByTenantId(@Param("tenantId") UUID tenantId);

}
