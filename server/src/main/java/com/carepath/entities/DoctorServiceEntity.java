package com.carepath.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "doctor_service", indexes = {
        @Index(name = "idx_doctorservice_hospital", columnList = "hospital_id")
})
@Getter
@Setter
@NoArgsConstructor
public class DoctorServiceEntity {
    @EmbeddedId
    private DoctorServiceId id;

    @MapsId("doctorId")
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @MapsId("serviceId")
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    public DoctorServiceEntity(DoctorEntity doctor, ServiceEntity service) {
        this.doctor = doctor;
        this.service = service;
        this.hospital = service.getHospital(); // lấy từ dịch vụ, không nhận từ request
        this.id = new DoctorServiceId(doctor.getId(), service.getId());
    }
}
