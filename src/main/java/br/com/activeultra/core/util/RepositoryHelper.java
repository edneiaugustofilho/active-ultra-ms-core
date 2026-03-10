package br.com.activeultra.core.util;

import br.com.activeultra.core.repository.SearchInput;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @SuppressWarnings("unchecked")
    public static <T> Predicate likeCaseInsensitive(Root<T> root, CriteriaBuilder cb, String property, String value) {
        List<String> values = List.of(value.split(" "));

        List<Predicate> predicates = new ArrayList<>();
        for (String v : values) {
            predicates.add(cb.like(cb.lower((Expression<String>) getPathByValue(root, property)), "%" + v.trim().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    public static <T> Predicate equalCaseInsensitive(Root<T> root, CriteriaBuilder cb, String property, String value) {
        return cb.equal(cb.lower(root.get(property)), value.toLowerCase());
    }

    public static Path<?> getPathByValue(Path<?> root, String value) {
        return getPathByValue(root, value, false);
    }

    public static Path<?> getPathByValue(Path<?> root, String value, boolean juntarAliasRootNoJoin) {
        Path<?> path = null;

        if (value.contains(".")) {
            From<?, ?> from = (From<?, ?>) root;

            String[] campos = value.split("\\.");
            String ultimoCampo = campos[campos.length - 1];

            for (int index = 0; index < campos.length - 1; index++) {
                String joinAlias = juntarAliasRootNoJoin ? from.getAlias() + campos[index] : campos[index];
                from = createJoin(from, campos[index], joinAlias, JoinType.LEFT);
            }

            try {
                path = getJoinWithElementCollection(from, ultimoCampo);
            } catch (IllegalArgumentException e) {
                tratarIllegalArgumentException(e, from, ultimoCampo);
            }

        } else {
            try {
                path = getJoinWithElementCollection(root, value);
            } catch (IllegalArgumentException e) {
                tratarIllegalArgumentException(e, root, value);
            }
        }

        return path;
    }


    public static Join<?, ?> getJoinByAlias(From<?, ?> from, String alias) {
        Join<?, ?> join = null;

        Set<? extends Join<?, ?>> joins = from.getJoins();

        if (!Objects.isNull(joins) && !joins.isEmpty()) {
            for (Join<?, ?> joinTemp : joins) {
                if (alias.equals(joinTemp.getAlias())) {
                    join = joinTemp;

                } else {
                    join = getJoinByAlias(joinTemp, alias);

                }

                if (!Objects.isNull(join)) {
                    break;
                }
            }
        }

        return join;
    }

    private static Path<?> getJoinWithElementCollection(Path<?> root, String fieldName) {
        Field field = FieldUtils.getField(root.getJavaType(), fieldName, true);
        if (field.isAnnotationPresent(ElementCollection.class)) {
            return createJoin((From<?, ?>) root, fieldName);
        }

        return root.get(fieldName);
    }

    /**
     * Se o join para o field especificado não existir, cria um join para o field do tipo Inner.<br/>
     * O nome do field será considerado como alias do join.
     *
     * @param from  um From
     * @param field um Field
     * @return Um Join
     */
    public static Join<?, ?> createJoin(From<?, ?> from, String field) {
        return createJoin(from, field, field);
    }

    /**
     * Se o join para o alias não existir, cria um join para o field com o alias especificado do tipo Inner.
     *
     * @param from  um From
     * @param field um String
     * @param alias um String
     * @return Um Join
     */
    public static Join<?, ?> createJoin(From<?, ?> from, String field, String alias) {
        return createJoin(from, field, alias, JoinType.INNER);
    }

    /**
     * Se o join para o field especificado não existir, cria um join para o field.<br/>
     * O nome do field será considerado como alias do join.
     *
     * @param from     um From
     * @param field    um String
     * @param joinType um JoinType
     * @return um Join
     */
    public static Join<?, ?> createJoin(From<?, ?> from, String field, JoinType joinType) {
        return createJoin(from, field, field, joinType);
    }

    /**
     * Se o join para o alias não existir, cria um join para o field com o alias especificado.
     *
     * @param from     um From
     * @param field    um String
     * @param alias    um String
     * @param joinType um JoinType
     * @return um Join
     */
    public static Join<?, ?> createJoin(From<?, ?> from, String field, String alias, JoinType joinType) {
        String[] fields = field.split("\\.");

        From<?, ?> nextFrom = from;

        if (fields.length > 0) {
            for (int index = 0; index < fields.length - 1; index++) {
                nextFrom = createJoin(nextFrom, fields[index]);
            }
            field = fields[fields.length - 1];
        }

        Join<?, ?> join = getJoinByAlias(nextFrom, alias);

        if (Objects.isNull(join)) {
            try {
                join = nextFrom.join(field, joinType);
                join.alias(alias);
            } catch (IllegalArgumentException e) {
                tratarIllegalArgumentException(e, nextFrom, field);

            }
        }

        return join;
    }

    public static List<String> getHierarquiaDePaths(Path<?> path) {
        List<String> parentsPaths = new ArrayList<>();

        Path<?> parentPath = path.getParentPath();
        if (Objects.nonNull(parentPath)) {
            parentsPaths.addAll(getHierarquiaDePaths(parentPath));
        }

        parentsPaths.add(path.getJavaType().getSimpleName());

        return parentsPaths;
    }

    public static void tratarIllegalArgumentException(IllegalArgumentException e, Path<?> path, String field) {
        List<String> hierarquiaDePaths = getHierarquiaDePaths(path);

        throw new IllegalArgumentException("O atributo \"" + field + "\" não foi encontrado em \"" + String.join(" > ", hierarquiaDePaths) + "\"", e);
    }

}
