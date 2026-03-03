package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetEventType;
import br.com.activeultra.core.enums.AssetStatus;
import br.com.activeultra.core.gateway.dto.AssetEventProcessRequest;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.util.EnumUtil;
import br.com.activeultra.core.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AssetEventProcessService {

    private final TenantService tenantService;
    private final AssetRepository assetRepository;
    private final AssetEventRecordService assetEventRecordService;

    public AssetEventProcessService(TenantService tenantService,
                                    AssetRepository assetRepository,
                                    AssetEventRecordService assetEventRecordService) {
        this.tenantService = tenantService;
        this.assetRepository = assetRepository;
        this.assetEventRecordService = assetEventRecordService;
    }

    @Transactional
    public void execute(AssetEventProcessRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Requisição inválida");
        }

        var tenantId = tenantService.getTenantId();

        final AssetEventType eventType;
        try {
            eventType = EnumUtil.parseEnum(AssetEventType.class, request.type());
        } catch (Exception e) {
            throw new IllegalArgumentException("Tipo de evento inválido: " + request.type(), e);
        }

        Asset asset = assetRepository.findByIdAndTenantId(request.assetId(), tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado: " + request.assetId()));

        final String notes;

        switch (eventType) {
            case STATUS_CHANGED -> {
                AssetStatus from = asset.getStatus();
                AssetStatus to;
                try {
                    to = EnumUtil.parseEnum(AssetStatus.class, request.newValue());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Status inválido: " + request.newValue(), e);
                }

                if (from == to) throw new IllegalArgumentException("Status já está em: " + to);

                notes = "Status alterado de [" + from + "] para [" + to + "]";
                assetRepository.updateStatus(to, asset.getId(), tenantId);
            }
            case CATEGORY_CHANGED -> {
                AssetCategory from = asset.getCategory();
                AssetCategory to;
                try {
                    to = EnumUtil.parseEnum(AssetCategory.class, request.newValue());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Categoria inválida: " + request.newValue(), e);
                }

                if (from == to) throw new IllegalArgumentException("Categoria já está em: " + to);

                notes = "Categoria alterada de [" + from + "] para [" + to + "]";
                assetRepository.updateCategory(to, asset.getId(), tenantId);
            }
            case LOCATION_CHANGED -> {
                String from = asset.getLocation();
                String to = StringUtils.trimOrNull(request.newValue());

                if (Objects.equals(from, to)) throw new IllegalArgumentException("Local já está em: " + to);

                notes = "Local alterado de [" + from + "] para [" + to + "]";
                assetRepository.updateLocation(to, asset.getId(), tenantId);
            }
            case RESPONSIBLE_CHANGED -> {
                String from = asset.getCurrentDriver();
                String to = StringUtils.trimOrNull(request.newValue());

                if (Objects.equals(from, to)) throw new IllegalArgumentException("Responsável já está em: " + to);

                notes = "Responsável alterado de [" + from + "] para [" + to + "]";
                assetRepository.updateCurrentDriver(to, asset.getId(), tenantId);
            }
            default -> throw new IllegalArgumentException("Tipo de evento não suportado: " + eventType);
        }

        assetEventRecordService.execute(eventType, notes, asset.getId());
    }


}
