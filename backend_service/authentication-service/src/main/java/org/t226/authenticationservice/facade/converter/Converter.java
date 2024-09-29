package org.t226.authenticationservice.facade.converter;

public interface Converter<T1, T2> {

    T1 toModel(T2 dto);

    T2 toDto(T1 model);
}
