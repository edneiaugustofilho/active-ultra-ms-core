package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.AssetEvent;
import br.com.activeultra.core.gateway.dto.AssetEventDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetEventRepository extends JpaRepository<AssetEvent, UUID>, SearchRepository<AssetEventDto, AssetEvent> {

    Optional<List<AssetEvent>> findAllByAssetIdAndTenantId(UUID assetId, UUID tenantId);

}
