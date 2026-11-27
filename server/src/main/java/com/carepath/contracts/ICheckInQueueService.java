package com.carepath.contracts;

import java.time.ZonedDateTime;
import java.util.List;

import com.carepath.entities.enums.CheckInMethod;
import com.carepath.entities.enums.QueueStatus;

/**
 * ICheckInQueueService: Quản lý quy trình xác nhận bệnh nhân đến khám (Check-in),
 * xử lý đến trễ, đánh dấu không đến (No-show) và điều phối hàng đợi khám bệnh trong ngày.
 *
 * Các bảng liên quan:
 * - Bảng chính: CheckIn (*), QueueEntry (*), Appointment (*), AppointmentStatusHistory (*)
 * - Bảng tham chiếu & đồng bộ: HospitalPolicy, ReceptionWindow, CapacityCounter
 */
public interface ICheckInQueueService {

    /**
     * Xác nhận bệnh nhân đã đến cơ sở y tế (Check-in): <br>
     * - Kiểm tra Appointment.status == BOOKED và thời gian check-in hợp lệ theo HospitalPolicy (ITenantService.validatePolicyRule) <br>
     * - Tạo bản ghi CheckIn (unique appointmentId), đánh dấu isLate nếu quá checkinLateMin. <br>
     * - Cập nhật Appointment.status -> CHECKED_IN, ghi AppointmentStatusHistory và tạo QueueEntry (status = WAITING).
     * @param appointmentId
     * @param method
     * @param staffId
     * @return CheckIn
     */
    CheckIn checkIn(
            String appointmentId,
            CheckInMethod method,  // SELF | STAFF | KIOSK (khớp CheckInMethod enum)
            String staffId  // Có thể null nếu bệnh nhân tự check-in qua Kiosk/App
    );

    /**
     * Xử lý bệnh nhân đến trễ theo quyết định vận hành của bệnh viện:
     * giữ nguyên vị trí (KEEP), đưa xuống cuối hàng đợi (MOVE_TO_END) hoặc hủy lịch (CANCEL).
     * @param appointmentId
     * @param action
     */
    void handleLateArrival(
            String appointmentId,
            String action // KEEP | MOVE_TO_END | CANCEL
    );

    /**
     * Đánh dấu bệnh nhân không đến khám (NO_SHOW) khi vượt quá thời hạn noShowAfterMin trong HospitalPolicy;
     * cập nhật Appointment.status -> NO_SHOW và quyết định giải phóng chỗ CapacityCounter theo chính sách bệnh viện.
     * @param appointmentId
     * @return QueueEntry
     */
    QueueEntry markNoShow(
            String appointmentId
    );

    /**
     * Đưa một lượt khám vào trạng thái chờ trong hàng đợi (status = WAITING) và gán vị trí xếp hàng.
     * @param queueEntryId
     * @return QueueEntry
     */
    QueueEntry enqueue(
            String queueEntryId
    );

    /**
     * Sắp xếp lại thứ tự ưu tiên/vị trí (queuePosition) của danh sách lượt khám trong ngày theo quyết định của bệnh viện.
     * @param hospitalId
     * @param queueDate
     * @param orderedIds
     * @return List<QueueEntry>
     */
    List<QueueEntry> reorderQueue(
            String hospitalId,
            ZonedDateTime queueDate,
            List<String> orderedIds
    );

    /**
     * Gọi lượt bệnh nhân tiếp theo trong hàng đợi của bệnh viện trong ngày (chuyển QueueStatus sang CALLED).
     * @param hospitalId
     * @param queueDate
     * @return QueueEntry
     */
    QueueEntry callNext(
            String hospitalId,
            ZonedDateTime queueDate
    );

    /**
     * Đánh dấu hoàn thành lượt khám trong hàng đợi (chuyển QueueStatus sang DONE và đồng bộ Appointment).
     * @param queueEntryId
     * @return QueueEntry
     */
    QueueEntry completeQueueEntry(
            String queueEntryId
    );


    record CheckIn(
            String id,
            String appointmentId,
            String hospitalId,
            String checkedInById,
            CheckInMethod method, // SELF | STAFF | KIOSK
            ZonedDateTime checkedInAt,
            boolean isLate,
            String note
    ) {}

    record QueueEntry(
            String id,
            String appointmentId,
            String hospitalId,
            ZonedDateTime queueDate,
            Integer queuePosition,
            QueueStatus status, // WAITING | CALLED | IN_PROGRESS | DONE | SKIPPED
            boolean priorityFlag,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
    ) {}
}