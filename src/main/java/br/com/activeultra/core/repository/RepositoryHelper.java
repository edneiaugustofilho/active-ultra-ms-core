package br.com.activeultra.core.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryHelper {

    public static Pageable buildPageable(SearchInput input) {
        if (input.getPageNumber() > 0 && input.getPageSize() > 0) {
            int page = input.getPageNumber() - 1;
            int size = input.getPageSize();

            if (input.getSortBy() != null) {
                Sort sort = (input.getDirection() != null)
                        ? Sort.by(parseDirection(input.getDirection()), input.getSortBy())
                        : Sort.by(input.getSortBy());
                return PageRequest.of(page, size, sort);
            }

            return PageRequest.of(page, size);
        }

        return Pageable.unpaged();
    }

    private static Sort.Direction parseDirection(String direction) {
        if (direction == null) return Sort.Direction.ASC;
        return "DESC".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    public static <T> Predicate likeCaseInsensitive(Root<T> root, CriteriaBuilder cb, String property, String value) {
        List<String> values = List.of(value.split(" "));

        List<Predicate> predicates = new ArrayList<>();
        for (String v : values) {
            predicates.add(cb.like(cb.lower(root.get(property)), "%" + v.trim().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    public static <T> Predicate equalCaseInsensitive(Root<T> root, CriteriaBuilder cb, String property, String value) {
        return cb.equal(cb.lower(root.get(property)), value.toLowerCase());
    }

}
