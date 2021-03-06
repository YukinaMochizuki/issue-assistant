package tw.yukina.sitcon.issue.assistant.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@MappedSuperclass
@Getter
public class AbstractEntity {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private String id;
}