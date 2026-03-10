package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.entity.AssetEvent;
import br.com.activeultra.core.enums.AssetEventType;
import br.com.activeultra.core.util.StringUtils;

import java.util.List;
import java.util.UUID;

public record AssetEventDto(UUID id,
                            UUID assetId,
                            String assetDescription,
                            AssetEventType eventType,
                            UUID authorId,
                            String authorName,
                            String notes,
                            String summary) {

    public static AssetEventDto toDto(AssetEvent assetEvent) {
        if (assetEvent != null) {
            return new AssetEventDto(assetEvent.getId(),
                    assetEvent.getAsset().getId(),
                    buildAssetDescription(assetEvent),
                    assetEvent.getEventType(),
                    assetEvent.getActorUserId(),
                    assetEvent.getActorUserName(),
                    assetEvent.getNotes(),
                    assetEvent.getSummary());
        }

        return null;
    }

    private static String buildAssetDescription(AssetEvent assetEvent) {
        if (assetEvent != null && assetEvent.getAsset() != null) {
            return StringUtils.concatenate(List.of(assetEvent.getAsset().getName(), assetEvent.getAsset().getSerialNumber()), " ");
        }
        return "";
    }

}
