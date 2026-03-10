package br.com.activeultra.core.service;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetStatus;
import br.com.activeultra.core.gateway.dto.AssetResumeDto;
import br.com.activeultra.core.gateway.dto.PageResponse;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.util.RepositoryHelper;
import br.com.activeultra.core.repository.SearchInput;
import br.com.activeultra.core.util.EnumUtil;
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

@Service
public class AssetSearchService {

    private final AssetRepository assetRepository;
    private final TenantService tenantService;

    public AssetSearchService(AssetRepository assetRepository,
                              TenantService tenantService) {
        this.assetRepository = assetRepository;
        this.tenantService = tenantService;
    }

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AssetSearchInput extends SearchInput {

        private String query;
        private String status;
        private String category;

    }

    public PageResponse<AssetResumeDto> searchResume(AssetSearchInput input) {
        Specification<Asset> spec = ((root, _, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("tenantId"),
                    tenantService.getTenantId()));

            if (StringUtils.isNotBlankOrNull(input.getQuery())) {
                final String query = input.getQuery();

                Predicate queryPredicate = criteriaBuilder.or(
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "name", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "code", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "location", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "brand", query),
                        RepositoryHelper.likeCaseInsensitive(root, criteriaBuilder, "model", query)
                );

                predicates.add(queryPredicate);
            }
            if (input.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), EnumUtil.parseEnum(AssetStatus.class, input.getStatus())));
            }
            if (input.getCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), EnumUtil.parseEnum(AssetCategory.class, input.getCategory())));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return assetRepository.searchResume(spec, RepositoryHelper.buildPageable(input));
    }

}
