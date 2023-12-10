package com.webJava.library.helpers;

import org.springframework.data.domain.Page;
import com.webJava.library.dto.PageDto;

import java.util.function.Function;

public interface PageDtoMaker<E, D> {
    PageDto<D> makePageDto(Page<E> page, Function<E, D> mapFunction);
}
