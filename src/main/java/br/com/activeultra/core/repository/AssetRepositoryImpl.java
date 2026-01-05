package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.gateway.dto.AssetResumeDto;
import br.com.activeultra.core.gateway.dto.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AssetRepositoryImpl implements AssetSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResponse<AssetResumeDto> searchResume(Specification<Asset> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AssetResumeDto> query = cb.createQuery(AssetResumeDto.class);
        Root<Asset> root = query.from(Asset.class);

        query.select(cb.construct(
                AssetResumeDto.class,
                root.get("id"),
                root.get("code"),
                root.get("name"),
                root.get("status"),
                root.get("category")));

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, cb);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        TypedQuery<AssetResumeDto> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<AssetResumeDto> resultList = typedQuery.getResultList();

        Page<AssetResumeDto> page = new PageImpl<>(resultList, pageable, contQuery(spec));

        return new PageResponse<>(resultList, pageable.getPageNumber(),
                pageable.getPageSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }

    private long contQuery(Specification<Asset> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Asset> root = query.from(Asset.class);
        query.select(cb.count(root));

        if (spec != null) {
            Predicate cp = spec.toPredicate(root, query, cb);
            if (cp != null) {
                query.where(cp);
            }
        }

        return entityManager.createQuery(query).getSingleResult();
    }
}
