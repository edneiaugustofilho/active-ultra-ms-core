package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.gateway.dto.AssetResumeDto;
import br.com.activeultra.core.gateway.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AssetSearchRepository {

    PageResponse<AssetResumeDto> searchResume(Specification<Asset> spec, Pageable pageable);

}

