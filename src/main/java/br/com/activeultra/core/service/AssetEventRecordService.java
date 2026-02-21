package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.entity.AssetEvent;
import br.com.activeultra.core.enums.AssetEventType;
import br.com.activeultra.core.gateway.dto.AssetEventSaveRequest;
import br.com.activeultra.core.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssetEventRecordService {

    private final AssetEventService assetEventService;
    private final AssetRepository assetRepository;

    public AssetEventRecordService(AssetEventService assetEventService,
                                   AssetRepository assetRepository) {
        this.assetEventService = assetEventService;
        this.assetRepository = assetRepository;
    }

    public void execute(AssetEventType eventType, UUID assetId) {
        if (assetId == null) {
            throw new IllegalArgumentException("ID de Ativo não informado.");
        }
        Asset asset = assetRepository.findById(assetId).orElseThrow(() -> new IllegalArgumentException(""));

        AssetEvent assetEvent = AssetEvent.builder()
                .eventType(eventType)
                .asset(asset)
                .build();

        assetEventService.save(new AssetEventSaveRequest(assetEvent));
    }

}
