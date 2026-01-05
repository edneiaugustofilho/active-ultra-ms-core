package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.service.AssetSearchService;
import lombok.NonNull;

public record AssetSearchRequest(String query, String status, String category, @NonNull Integer pageNumber,
                                 @NonNull Integer pageSize, String direction, String sortBy) {

    public AssetSearchService.AssetSearchInput toSearchInput() {
        return AssetSearchService.AssetSearchInput.builder()
                .query(query)
                .status(status)
                .category(category)
                .pageNumber(pageNumber > 0 ? pageNumber : 1)
                .pageSize(pageSize > 0 ? pageSize : 10)
                .direction(direction)
                .sortBy(sortBy)
                .build();
    }

}
