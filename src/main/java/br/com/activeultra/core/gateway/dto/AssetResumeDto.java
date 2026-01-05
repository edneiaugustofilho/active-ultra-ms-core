package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetStatus;

import java.util.UUID;

public record AssetResumeDto(UUID id, String name, String code, AssetStatus status, AssetCategory category) {
}
