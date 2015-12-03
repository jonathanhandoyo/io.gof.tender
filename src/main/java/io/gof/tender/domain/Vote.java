package io.gof.tender.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gof.tender.util.CustomLongDateDeserializer;
import io.gof.tender.util.CustomLongDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {
    @JsonSerialize(using = CustomLongDateSerializer.class)
    @JsonDeserialize(using = CustomLongDateDeserializer.class)
    private Date timestamp;

    @Relationship(type = "BY")
    private User user;

    @Relationship(type = "FOR")
    private Project project;
}
