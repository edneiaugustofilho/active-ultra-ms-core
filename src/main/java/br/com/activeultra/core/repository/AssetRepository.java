package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetStatus;
import br.com.activeultra.core.gateway.dto.AssetByCategoryDto;
import br.com.activeultra.core.gateway.dto.AssetByStatusDto;
import br.com.activeultra.core.gateway.dto.AssetResumeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID>, SearchRepository<AssetResumeDto, Asset> {

    boolean existsByTenantIdAndCodeIgnoreCase(UUID tenantId, String code);

    boolean existsByTenantIdAndCodeIgnoreCaseAndIdNot(UUID tenantId, String code, UUID id);

    Optional<Asset> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<Asset> findByTenantIdAndCode(UUID tenantId, String code);

    @Query("SELECT count(*) AS total FROM Asset a WHERE a.tenantId = :tenantId AND a.status != 'INACTIVE'")
    Long countByTenantId(@Param("tenantId") UUID tenantId);

    @Query("SELECT COALESCE(SUM(a.acquisitionValue), 0) AS total FROM Asset a WHERE a.tenantId = :tenantId AND a.status != 'INACTIVE'")
    BigDecimal sumAcquisitionValueByTenantid(@Param("tenantId") UUID tenantId);

    @Query("SELECT " +
            "new br.com.activeultra.core.gateway.dto." +
            "AssetByStatusDto(a.status as status, COUNT(*) AS total, COALESCE(SUM(a.acquisitionValue), 0) AS totalAcquisitionValue) " +
            "FROM Asset a WHERE a.tenantId = :tenantId AND a.status != 'INACTIVE' GROUP BY a.status")
    List<AssetByStatusDto> countByStatus(@Param("tenantId") UUID tenantId);

    @Query("SELECT " +
            "new br.com.activeultra.core.gateway.dto." +
            "AssetByCategoryDto(a.category as category, COUNT(*) AS total, COALESCE(SUM(a.acquisitionValue), 0) AS totalAcquisitionValue) " +
            "FROM Asset a WHERE a.tenantId = :tenantId AND a.category != 'INACTIVE' GROUP BY a.category")
    List<AssetByCategoryDto> countByCategory(@Param("tenantId") UUID tenantId);

    boolean existsByIdAndTenantId(UUID id, UUID tenantId);

    @Modifying
    @Query("UPDATE Asset SET status = :status WHERE id = :assetId AND tenantId = :tenantId")
    void updateStatus(AssetStatus status, UUID assetId, UUID tenantId);

    @Modifying
    @Query("UPDATE Asset SET category = :category WHERE id = :assetId AND tenantId = :tenantId")
    void updateCategory(AssetCategory category, UUID assetId, UUID tenantId);

    @Modifying
    @Query("UPDATE Asset SET location = :location WHERE id = :assetId AND tenantId = :tenantId")
    void updateLocation(String newLocation, UUID assetId, UUID tenantId);

    @Modifying
    @Query("UPDATE Asset SET currentDriver = :currentDriver WHERE id = :assetId AND tenantId = :tenantId")
    void updateCurrentDriver(String newCurrentDriver, UUID assetId, UUID tenantId);
}
