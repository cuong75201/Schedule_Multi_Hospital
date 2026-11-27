package com.carepath.contracts;

import java.time.ZonedDateTime;
import java.util.List;

import com.carepath.entities.enums.NotificationChannel;
import com.carepath.entities.enums.NotificationStatus;

/**
 * INotificationOutboxService: Triển khai Transactional Outbox Pattern để quản lý hàng đợi
 * gửi thông báo bất đồng bộ (SMS, EMAIL, PUSH) và cơ chế thử lại khi gặp lỗi.
 *
 * Các bảng liên quan:
 * - Bảng chính: NotificationOutbox (*)
 * - Bảng tham chiếu: Hospital, Patient
 */
public interface INotificationOutboxService {

    /**
     * Đưa yêu cầu gửi thông báo vào hàng đợi NotificationOutbox (status = PENDING, retryCount = 0). <br>
     * Bắt buộc gọi trong CÙNG transaction (@Transactional) với nghiệp vụ chính (đặt/đổi/hủy lịch).
     * @param hospitalId
     * @param patientId
     * @param channel
     * @param templateCode
     * @param payload
     * @return NotificationOutbox
     */
    NotificationOutbox enqueue(
            String hospitalId,   // Có thể null nếu là thông báo cấp hệ thống
            String patientId,    // Có thể null
            NotificationChannel channel,      // SMS | EMAIL | PUSH (khớp NotificationChannel enum)
            String templateCode,
            String payload       // Chuỗi JSON truyền vào mẫu thông báo (khớp NotificationOutbox.payload)
    );

    /**
     * Đánh dấu bản ghi thông báo đã gửi thành công (status = SENT, cập nhật sentAt = now()).
     * @param outboxId
     */
    void markSent(
            Long outboxId
    );

    /**
     * Đánh dấu lần gửi thông báo thất bại (status = FAILED, tăng retryCount + 1, lưu errorMessage).
     * @param outboxId
     * @param error
     */
    void markFailed(
            Long outboxId,
            String error
    );

    /**
     * Truy xuất và xử lý gửi lại các thông báo đang ở trạng thái PENDING hoặc FAILED (còn lượt retry)
     * với giới hạn số lượng mỗi đợt chạy (dùng cho Background Worker / Scheduled Job).
     * @param limit
     * @return List<NotificationOutbox>
     */
    List<NotificationOutbox> retryPending(
            int limit
    );


    record NotificationOutbox(
            Long id, 
            String hospitalId,
            String patientId,
            NotificationChannel channel,     
            String templateCode,
            String payload,      // JSON string
            NotificationStatus status,       
            int retryCount,
            ZonedDateTime createdAt,
            ZonedDateTime sentAt,
            String errorMessage
    ) {}
}