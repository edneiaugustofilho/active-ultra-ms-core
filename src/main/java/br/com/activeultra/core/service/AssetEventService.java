package br.com.activeultra.core.service;

import br.com.activeultra.core.actor.ActorProvider;
import br.com.activeultra.core.entity.AssetEvent;
import br.com.activeultra.core.gateway.dto.AssetEventSaveRequest;
import br.com.activeultra.core.repository.AssetEventRepository;
import br.com.activeultra.core.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetEventService {

    private final AssetRepository assetRepository;
    private final AssetEventRepository assetEventRepository;
    private final TenantService tenantService;
    private final ActorProvider actorProvider;

    public AssetEventService(AssetRepository assetRepository,
                             AssetEventRepository assetEventRepository,
                             TenantService tenantService,
                             ActorProvider actorProvider) {
        this.assetRepository = assetRepository;
        this.assetEventRepository = assetEventRepository;
        this.tenantService = tenantService;
        this.actorProvider = actorProvider;
    }

    private void validate(AssetEventSaveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Evento inválido.");
        }

        AssetEvent assetEvent = request.assetEvent();
        if (assetEvent == null) {
            throw new IllegalArgumentException("Evento não pode ser nulo.");
        }
        if (assetEvent.getId() != null) {
            throw new IllegalArgumentException("Evento não pode ser alterado.");
        }
        if (assetEvent.getAsset() == null) {
            throw new IllegalArgumentException("Ativo não pode ser nulo.");
        }
        if (assetEvent.getAsset().getId() == null ||
                assetEvent.getAsset().getTenantId() == null) {
            throw new IllegalArgumentException("Ativo não encontrado.");
        }
        if (assetEvent.getEventType() == null) {
            throw new IllegalArgumentException("Tipo de Evento inválido.");
        }
    }

    private void fillAuthor(AssetEvent assetEvent) {
        assetEvent.setActorUserId(actorProvider.getCurrentUserid());
    }

    @Transactional
    public void save(AssetEventSaveRequest request) {
        validate(request);

        AssetEvent assetEvent = request.assetEvent();
        assetEvent.setTenantId(tenantService.getTenantId());
        fillAuthor(assetEvent);


        assetEventRepository.save(request.assetEvent());
    }

}
