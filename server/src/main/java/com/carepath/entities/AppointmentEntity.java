package com.carepath.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.carepath.entities.enums.AppointmentStatus;
import com.carepath.entities.enums.CancelledBy;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointment", indexes = {
        @Index(name = "idx_appointment_patient_status", columnList = "patient_id, status"),
        @Index(name = "idx_appointment_window_status", columnList = "window_id, status"),
        @Index(name = "idx_appointment_hospital_status", columnList = "hospital_id, status")
})
@Getter
@Setter
@NoArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String bookingCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "window_id", nullable = false)
    private ReceptionWindowEntity window;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.BOOKED;

    @OneToOne
    @JoinColumn(name = "superseded_by_id", unique = true)
    private AppointmentEntity supersededBy;

    @ManyToOne
    @JoinColumn(name = "parent_appointment_id")
    private AppointmentEntity parentAppointment;

    @Column(nullable = false)
    private boolean priorityFlag = false;

    @ManyToOne
    @JoinColumn(name = "priority_set_by_id")
    private HospitalStaffEntity prioritySetBy;

    private String priorityReason;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private CancelledBy cancelledBy;

    private String cancelReason;

    private LocalDateTime cancelledAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime bookedAt;

    private LocalDateTime checkedInAt;

    private LocalDateTime completedAt;
}
