package com.carepath.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Table Doctor {
//   id String [pk, default: `uuid()`, note: 'Định danh duy nhất của bác sĩ']
//   fullName String [not null, note: 'Họ tên đầy đủ của bác sĩ']
//   licenseNo String [note: 'Số giấy phép hành nghề']
//   phone String [note: 'Số điện thoại liên hệ']
//   email String [note: 'Địa chỉ email']
//   status EntityStatus [not null, default: 'ACTIVE', note: 'Trạng thái hoạt động của bác sĩ']
//   createdAt DateTime [not null, default: `now()`, note: 'Thời điểm tạo hồ sơ bác sĩ']

//   Note: 'Lưu hồ sơ bác sĩ ở cấp hệ thống. Một bác sĩ có thể làm việc tại nhiều bệnh viện.'
// }

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String fullName;
    private String licenseNo;
    @Column(length = 20)
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private EntityStatus status = EntityStatus.ACTIVE;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
