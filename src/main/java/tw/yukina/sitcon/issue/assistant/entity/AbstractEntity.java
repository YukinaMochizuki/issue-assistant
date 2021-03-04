package tw.yukina.sitcon.issue.assistant.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@MappedSuperclass
@Getter
public class AbstractEntity {
    @Id
    @Column(unique = true)
    @NotNull
    private String id;
}