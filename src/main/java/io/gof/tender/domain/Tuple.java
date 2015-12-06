package io.gof.tender.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Tuple extends BaseEntity {
    private String code;
    private String name;
}
