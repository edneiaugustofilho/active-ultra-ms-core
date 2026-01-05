package br.com.activeultra.core.repository;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class SearchInput {

    protected int pageNumber;
    protected int pageSize;

    protected String direction;
    protected String sortBy;

}
