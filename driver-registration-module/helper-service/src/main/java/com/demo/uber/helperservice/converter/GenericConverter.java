package com.demo.uber.helperservice.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericConverter<E, D> {
    E createFromDto(D dto);

    D createFromEntity(E entity);

    E updateEntity(E entity,  D dto);

    default List<D> createFromEntity(List<E> entities) {
        return entities.stream()
                .map(this::createFromEntity)
                .collect(Collectors.toList());
    }

    default List<E> createFromDto(List<D> dtos) {
        return dtos.stream()
                .map(this::createFromDto)
                .collect(Collectors.toList());
    }
}