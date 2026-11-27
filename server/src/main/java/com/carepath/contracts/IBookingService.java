package com.carepath.contracts;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * IBookingService: Quản lý toàn bộ nghiệp vụ đặt lịch khám online (đặt theo dịch vụ),
 * hủy lịch, đổi lịch và kiểm tra sức chứa khả dụng của khung tiếp nhận.
 *
 * Các bảng liên quan:
 * - Bảng chính: Appointment (*), CapacityCounter (*), AppointmentStatusHistory (*)
 * - Bảng tham chiếu & đồng bộ: ReceptionWindow, HospitalPolicy, Service, Patient, NotificationOutbox
 */
public interface IBookingService {

        /**
        * Bệnh nhân đặt lịch khám trực tuyến
        * Kiểm tra policy -> ITenantService.validatePolicyRule
        * tăng CapacityCounter bằng Optimistic Locking, tạo Appointment (BOOKED) và đẩy thông báo vào NotificationOutbox.
        * Phương thức dùng chung cho cả TH cho phép chọn BS và k cho phép chọn BS
        * @param patientId
        * @param hospitalId
        * @param serviceId
        * @param windowId
        * @param doctorId
        * @return Appointment
        */
        Appointment createOnlineBooking(
                String patientId,
                String hospitalId,
                String serviceId,
                String windowId,
                String doctorId // Có thể null nếu Service.requiresDoctorSelection = false
        );

        /**
        * Hủy lịch khám bởi Bệnh nhân, Bệnh viện hoặc Hệ thống:
        * - Nếu actorType = PATIENT: kiểm tra thời hạn cancelCutoffMin theo HospitalPolicy (ITenantService.validatePolicyRule).
        * - Chuyển status sang CANCELLED, giảm CapacityCounter.bookedCount, ghi lịch sử và gửi thông báo.
        * @param appointmentId
        * @param actorType
        * @param reason
        * @return Appointment
        */
        Appointment cancelAppointment(
                String appointmentId,
                String actorType, // PATIENT | HOSPITAL | SYSTEM (khớp CancelledBy enum)
                String reason
        );

        /**
        * Đổi lịch khám sang khung tiếp nhận mới (2-phase optimistic counter):
        * - Tạo bản ghi Appointment mới (gắn parentAppointmentId = lịch gốc, status = BOOKED).
        * - Chuyển lịch cũ sang RESCHEDULED và gắn supersededById = ID lịch mới.
        * - Giảm CapacityCounter khung cũ và tăng CapacityCounter khung mới.
        * @param appointmentId
        * @param newWindowId
        * @param newDoctorId
        * @return Appointment
        */
        Appointment rescheduleAppointment(
                String appointmentId,
                String newWindowId,
                String newDoctorId // Có thể null
        );

        /**
        * Hàm tiện ích: lấy thông tin chi tiết đơn đăng ký -> dùng cho rescheduleAppointment
        * @param appoointmentId
        * @return Appointment
        */
        Appointment getAppointment(
                String appoointmentId
        );

        record Appointment(
                String id,
                String bookingCode,
                String hospitalId,
                String patientId,
                String serviceId,
                String windowId,
                String doctorId,
                LocalDate windowDate,
                // LocalDateTime estimatedStartTime,        // tương tự như startTime trong ReceptionWindow -> đổi tên để chỉ rõ thời gian khám dự kiến
                // LocalDateTime estimatedEndTime,
                // String timeDisclaimer, // Lưu ý giờ đăng ký là khung tiếp nhận dự kiến -> đánh dấu thời gian đăng ký
                String status,         // BOOKED, CHECKED_IN, WAITING, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW, RESCHEDULED
                String supersededById,
                String parentAppointmentId,
                String cancelledBy,
                String cancelReason,
                ZonedDateTime bookedAt,
                ZonedDateTime checkedInAt,
                ZonedDateTime completeAt
        ) {}
}