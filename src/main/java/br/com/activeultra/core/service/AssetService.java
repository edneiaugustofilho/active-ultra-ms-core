package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetEventType;
import br.com.activeultra.core.enums.AssetStatus;
import br.com.activeultra.core.gateway.dto.AssetUpsertRequest;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AssetService {

    private final AssetRepository repository;
    private final AssetDataValidationService validationService;
    private final AssetEventRecordService assetEventRecordService;
    private final TenantService tenantService;

    public AssetService(AssetRepository repository,
                        AssetDataValidationService validationService,
                        AssetEventRecordService assetEventRecordService,
                        TenantService tenantService) {
        this.repository = repository;
        this.validationService = validationService;
        this.assetEventRecordService = assetEventRecordService;
        this.tenantService = tenantService;
    }

    public Asset findById(UUID id) {
        return repository.findByIdAndTenantId(id, tenantService.getTenantId()).orElse(null);
    }

    public Asset findByCode(String code) {
        return repository.findByTenantIdAndCode(tenantService.getTenantId(), code).orElse(null);
    }

    @Transactional
    public Asset create(AssetUpsertRequest request) {
        validationService.validate(request, null);

        Asset asset = AssetUpsertRequest.upsertToEntity(request);
        asset.setTenantId(tenantService.getTenantId());
        asset.setStatus(AssetStatus.ACTIVE);

        repository.save(asset);
        assetEventRecordService.execute(AssetEventType.CREATED, asset.getId());

        return asset;
    }

    @Transactional
    public Asset update(AssetUpsertRequest request, UUID id) {
        Asset asset = repository.findByIdAndTenantId(id, tenantService.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        validationService.validate(request, id);

        AssetUpsertRequest.upsertToEntityButLockedFields(request, asset);
        asset.setTenantId(tenantService.getTenantId());

        return repository.save(asset);
    }

    @Transactional
    public void delete(UUID id) {
        Asset asset = findById(id);
        if (asset == null) {
            throw new IllegalArgumentException("Asset not found");
        }

        asset.setStatus(AssetStatus.INACTIVE);
        repository.save(asset);
        assetEventRecordService.execute(AssetEventType.EXCLUDED, asset.getId());
    }

}
