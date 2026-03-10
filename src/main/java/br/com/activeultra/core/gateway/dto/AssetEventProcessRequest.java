package br.com.activeultra.core.gateway.dto;

import java.util.UUID;

public record AssetEventProcessRequest(UUID assetId, String type, String newValue, String notes) {
}
