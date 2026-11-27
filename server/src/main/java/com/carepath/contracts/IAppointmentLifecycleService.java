package com.carepath.contracts;

import java.time.ZonedDateTime;
import java.util.List;

import com.carepath.entities.enums.AppointmentStatus;

/**
 * IAppointmentLifecycleService: Quản lý máy trạng thái (State Machine) của lịch khám,
 * thiết lập quyền ưu tiên tiếp nhận và truy vết lịch sử chuyển đổi trạng thái.
 *
 * Các bảng liên quan:
 * - Bảng chính: Appointment (*), AppointmentStatusHistory (*)
 * - Bảng tham chiếu: HospitalStaff, QueueEntry
 */
public interface IAppointmentLifecycleService {

    /**
     * Chuyển đổi trạng thái của lịch khám theo State Machine hợp lệ và ghi nhận lịch sử vào AppointmentStatusHistory: <br>
     * - Luồng chính: BOOKED -> CHECKED_IN -> WAITING -> IN_PROGRESS -> COMPLETED <br>
     * - Nhánh ngoại lệ: BOOKED -> CANCELLED | NO_SHOW | RESCHEDULED
     * @param appointmentId
     * @param toStatus
     * @param changedBy
     * @param note
     * @return IBookingService.Appointment
     */
    IBookingService.Appointment transition(
            String appointmentId,
            AppointmentStatus toStatus,
            String changedBy, // ID người thay đổi
            String note       // Có thể null
    );

    /**
     * Lấy toàn bộ lịch sử chuyển đổi trạng thái của một lịch khám sắp xếp theo thời gian.
     * @param appointmentId
     * @return List<AppointmentStatusHistory>
     */
    List<AppointmentStatusHistory> getHistory(
            String appointmentId
    );


    record AppointmentStatusHistory(
            Long id, 
            String appointmentId,
            AppointmentStatus fromStatus, // Null nếu là lần khởi tạo đầu tiên (với trường hợp dùng String) -> Đang dùng ENUM
            AppointmentStatus toStatus,
            String changedBy,
            ZonedDateTime changedAt,
            String note
    ) {}
}