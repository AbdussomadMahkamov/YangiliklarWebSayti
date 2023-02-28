package com.example.yangiliklarwebsaytibackend.entity.templated;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstaktEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp yaratilganVaqt;
    @UpdateTimestamp
    private Timestamp tahrirlanganVaqt;
    @CreatedBy
    private Integer yaratuvchiId;
    @LastModifiedBy
    private Integer tahrirlashId;
}
