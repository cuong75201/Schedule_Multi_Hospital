package com.carepath.entities;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.carepath.entities.enums.EntityStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service", uniqueConstraints = {
        @UniqueConstraint(name = "uk_service_hospital_code", columnNames = { "hospital_id", "code" })
}, indexes = {
        @Index(name = "idx_service_hospital", columnList = "hospital_id")
})
@Getter
@Setter
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private Integer durationMin;
    @Column(precision = 12, scale = 0)
    private BigDecimal price;
    @Column(nullable = false)
    private boolean requiresDoctorSelection = false;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private EntityStatus status = EntityStatus.ACTIVE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

}
