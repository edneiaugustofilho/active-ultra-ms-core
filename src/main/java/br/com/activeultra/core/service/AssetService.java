package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetStatus;
import br.com.activeultra.core.gateway.dto.AssetUpsertRequest;
import br.com.activeultra.core.mapper.AssetMapper;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AssetService extends TenantService {

    private final AssetRepository repository;
    private final AssetDataValidationService validationService;

    public AssetService(AssetRepository repository, AssetDataValidationService validationService, TenantContext tenantContext) {
        super(tenantContext);
        this.validationService = validationService;
        this.repository = repository;
    }

    public Asset findById(UUID id) {
        return repository.findByIdAndTenantId(id, getTenantId()).orElse(null);
    }

    public Asset findByCode(String code) {
        return repository.findByTenantIdAndCode(getTenantId(), code).orElse(null);
    }

    @Transactional
    public Asset create(AssetUpsertRequest request) {
        validationService.validate(request, null);

        Asset asset = request.toEntity();
        asset.setTenantId(getTenantId());
        asset.setStatus(AssetStatus.ACTIVE);

        return repository.save(asset);
    }

    @Transactional
    public Asset update(AssetUpsertRequest request, UUID id) {
        Asset asset = repository.findByIdAndTenantId(id, getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        validationService.validate(request, id);

        AssetMapper.upsertIntoEntity(request, asset);
        asset.setTenantId(getTenantId());

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
    }

}
