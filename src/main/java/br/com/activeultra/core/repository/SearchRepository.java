package br.com.activeultra.core.repository;

import br.com.activeultra.core.gateway.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SearchRepository<T, P> {

    PageResponse<T> searchResume(Specification<P> spec, Pageable pageable);

}
