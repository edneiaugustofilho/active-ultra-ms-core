package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.enums.AssetEventType;

import java.util.UUID;

public record AssetEventProcessRequest(UUID assetId, String type, String newValue, String notes) {
}
