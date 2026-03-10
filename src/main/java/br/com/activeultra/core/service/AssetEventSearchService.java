package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.AssetEvent;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetStatus;
import br.com.activeultra.core.gateway.dto.AssetEventDto;
import br.com.activeultra.core.gateway.dto.PageResponse;
import br.com.activeultra.core.repository.AssetEventRepository;
import br.com.activeultra.core.util.EnumUtil;
import br.com.activeultra.core.util.RepositoryHelper;
import br.com.activeultra.core.repository.SearchInput;
import br.com.activeultra.core.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AssetEventSearchService {

    private final AssetEventRepository assetEventRepository;
    private final TenantService tenantService;

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AssetEventSearchInput extends SearchInput {
        private String query;
        private UUID tenantId;
        private String status;
        private String category;

    }

    public AssetEventSearchService(AssetEventRepository assetEventRepository,
                                   TenantService tenantService) {
        this.assetEventRepository = assetEventRepository;
        this.tenantService = tenantService;
    }

    public PageResponse<AssetEventDto> searchResume(AssetEventSearchInput input) {
        Specification<AssetEvent> spec = ((root, _, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlankOrNull(input.getQuery())) {
                final String query = input.getQuery();

                Predicate queryPredicate = criteriaBuilder.or(
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "asset.name", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "asset.code", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "asset.location", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "asset.brand", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "asset.model", query)
                );

                predicates.add(queryPredicate);
            }
            if (input.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("asset.status"), EnumUtil.parseEnum(AssetStatus.class, input.getStatus())));
            }
            if (input.getCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("asset.category"), EnumUtil.parseEnum(AssetCategory.class, input.getCategory())));
            }


            predicates.add(criteriaBuilder.equal(root.get("tenantId"),
                    tenantService.getTenantId()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return assetEventRepository.searchResume(spec, RepositoryHelper.buildPageable(input));
    }
}
