package br.com.activeultra.core.repository;

import br.com.activeultra.core.entity.AssetEvent;
import br.com.activeultra.core.gateway.dto.AssetEventDto;
import br.com.activeultra.core.gateway.dto.PageResponse;
import br.com.activeultra.core.util.RepositoryHelper;
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

public class AssetEventRepositoryImpl implements SearchRepository<AssetEventDto, AssetEvent> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResponse<AssetEventDto> searchResume(Specification<AssetEvent> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<AssetEventDto> query = cb.createQuery(AssetEventDto.class);
        Root<AssetEvent> root = query.from(AssetEvent.class);

        query.select(cb.construct(
                AssetEventDto.class,
                root.get("id"),
                RepositoryHelper.getPathByValue(root, "asset.id"),
                RepositoryHelper.getPathByValue(root, "asset.name"),
                root.get("eventType"),
                root.get("actorUserId"),
                root.get("actorUserName"),
                root.get("notes"),
                root.get("summary")));

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, cb);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        TypedQuery<AssetEventDto> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<AssetEventDto> resultList = typedQuery.getResultList();

        Page<AssetEventDto> page = new PageImpl<>(resultList, pageable, contQuery(spec));

        return new PageResponse<>(resultList, pageable.getPageNumber(),
                pageable.getPageSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }

    private long contQuery(Specification<AssetEvent> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<AssetEvent> root = query.from(AssetEvent.class);
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
