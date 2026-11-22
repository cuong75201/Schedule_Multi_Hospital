package com.carepath.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DoctorServiceId implements Serializable {
    @Column (name = "doctor_id",nullable=false)
    private UUID doctorId;
    @Column(name = "service_id", nullable = false)
    private  UUID serviceId;

}
