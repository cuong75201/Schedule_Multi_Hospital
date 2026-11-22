package com.carepath.entities;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.carepath.entities.enums.EntityStatus;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Doctor_hospital")
@Getter
@Setter
@NoArgsConstructor
public class DoctorHospitalEntity {
    @EmbeddedId
    private DoctorHospitalId id;

    @MapsId("doctorId")
    @ManyToOne
    @JoinColumn(name="doctor_id",nullable = false)
    private DoctorEntity doctor;

    @MapsId("hospitalId")
    @ManyToOne
    @JoinColumn(name = "hospital_id",nullable = false)
    private HospitalEntity hospital;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EntityStatus status = EntityStatus.ACTIVE;
}
