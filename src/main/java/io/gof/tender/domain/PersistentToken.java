package io.gof.tender.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
public class PersistentToken extends BaseEntity{
    @NotEmpty
    private String series;

    @JsonIgnore
    @NotEmpty
    private String tokenValue;

    @JsonIgnore
    private LocalDate tokenDate;

    //an IPV6 address max length is 39 characters
    @Size(min = 0, max = 39)
    private String ipAddress;


    private String userAgent;

    @JsonIgnore

    @Relationship(type = "OWNED_BY")
    private User user;
}
