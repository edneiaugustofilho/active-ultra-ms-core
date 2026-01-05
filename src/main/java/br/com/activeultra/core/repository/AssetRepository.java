package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID>, JpaSpecificationExecutor<Asset>, AssetSearchRepository {

    boolean existsByTenantIdAndCodeIgnoreCase(UUID tenantId, String code);

    boolean existsByTenantIdAndCodeIgnoreCaseAndIdNot(UUID tenantId, String code, UUID id);

    Optional<List<Asset>> findAllByTenantId(UUID tenantId);

    Optional<Asset> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<Asset> findByTenantIdAndCode(UUID tenantId, String code);

}
