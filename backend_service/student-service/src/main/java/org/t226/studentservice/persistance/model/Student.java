package org.t226.studentservice.persistance.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.t226.studentservice.persistance.converter.JpaContentConvertorJson;
import org.t226.studentservice.persistance.model.domain.StudentProperties;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "uuid")
    private String uuid;
    @Convert(converter = JpaContentConvertorJson.class)
    @Column(columnDefinition = "jsonb", name = "data")
    private StudentProperties data;
    @Column(name = "isvalid")
    private boolean valid;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
