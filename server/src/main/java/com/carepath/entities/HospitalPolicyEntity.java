package com.carepath.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Table HospitalPolicy {
//   id String [pk, default: `uuid()`, note: 'Định danh chính sách']
//   hospitalId String [not null, ref: > Hospital.id, note: 'Bệnh viện áp dụng chính sách']
//   serviceId String [ref: > Service.id, note: 'Dịch vụ áp dụng chính sách riêng; NULL biểu thị chính sách mặc định của bệnh viện']
//   bookingAdvanceDays Int [not null, default: 30, note: 'Số ngày tối đa bệnh nhân được phép đặt lịch trước']
//   allowOverbooking Boolean [not null, default: false, note: 'Cho phép đặt lịch vượt quá sức chứa tiêu chuẩn']
//   overbookingLimitPct Decimal [not null, default: 0, note: 'Tỷ lệ phần trăm sức chứa được phép đặt vượt']
//   preventDuplicateBooking Boolean [not null, default: true, note: 'Ngăn bệnh nhân tạo các lịch khám bị trùng lặp']
//   checkinEarlyMin Int [not null, default: 30, note: 'Số phút bệnh nhân được phép check-in trước giờ hẹn']
//   checkinLateMin Int [not null, default: 15, note: 'Số phút bệnh nhân được phép check-in trễ']
//   cancelCutoffMin Int [not null, default: 120, note: 'Thời gian tối thiểu trước giờ hẹn để được hủy lịch']
//   allowSelfCancelAfterCutoff Boolean [not null, default: false, note: 'Cho phép bệnh nhân tự hủy lịch sau thời hạn hủy thông thường']
//   noShowAfterMin Int [not null, default: 30, note: 'Khoảng thời gian sau giờ hẹn để xác định bệnh nhân không đến']
//   priorityPolicyEnabled Boolean [not null, default: true, note: 'Bật hoặc tắt chính sách ưu tiên trong quy trình tiếp nhận']
//   createdAt DateTime [not null, default: `now()`, note: 'Thời điểm tạo chính sách']

//   indexes {
//     (hospitalId, serviceId) [unique]
//   }

//   Note: 'Lưu cấu hình và quy tắc đặt lịch theo bệnh viện hoặc từng dịch vụ. Cho phép các bệnh viện vận hành với chính sách khác nhau mà không cần thay đổi mã nguồn.'
// }

@Entity
@Table(name = "hospital_policy", uniqueConstraints = {
        @UniqueConstraint(name = "uk_hospitalPolicy_hostpital_service", columnNames = { "hospital_id", "service_id" })
})
@Getter
@Setter
@NoArgsConstructor
public class HospitalPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(nullable = false)
    private int bookingAdvanceDays = 30;
    @Column(nullable = false)
    private boolean allowOverbooking = false;
    @Column(nullable = false)
    private BigDecimal overbookingLimitPct = BigDecimal.ZERO;
    @Column(nullable = false)
    private boolean preventDuplicateBooking = true;
    @Column(nullable = false)
    private int checkinEarlyMin = 30;
    @Column(nullable = false)
    private int checkinLateMin = 15;
    @Column(nullable = false)
    private int cancelCutoffMin = 120;
    @Column(nullable = false)
    private boolean allowSelfCancelAfterCutoff = false;
    @Column(nullable = false)
    private int noShowAfterMin = 30;
    @Column(nullable = false)
    private boolean priorityPolicyEnabled = true;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
