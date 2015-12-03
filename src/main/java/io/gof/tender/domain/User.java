package io.gof.tender.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private boolean activated = false;
    private String avatarSource;
    private String avatarBase64;
    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public User(String username, String password, String avatarSource, String avatarBase64) {
        this.username = username;
        this.password = password;
        this.avatarSource = avatarSource;
        this.avatarBase64 = avatarBase64;
    }
}
