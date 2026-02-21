package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.AssetEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetEventRepository extends JpaRepository<AssetEvent, UUID> {

    Optional<List<AssetEvent>> findAllByAssetId(UUID assetId);

}
