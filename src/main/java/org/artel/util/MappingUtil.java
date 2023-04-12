package org.artel.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MappingUtil {

    ModelMapper modelMapper;

    public <E, D> E toEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <E, D> D toDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <E, D> List<E> toEntityList(List<D> dtos, Class<E> entityClass) {
        return dtos.stream()
                .map(d -> toEntity(d, entityClass))
                .collect(Collectors.toList());
    }

    public <E, D> List<D> toDtoList(List<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(e -> toDto(e, dtoClass))
                .collect(Collectors.toList());
    }
}
