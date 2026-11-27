package com.carepath.contracts;

import java.time.ZonedDateTime;

/**
 * IAuditService: Quản lý nhật ký kiểm toán toàn hệ thống, ghi nhận mọi thao tác
 * tạo, cập nhật, xóa và thay đổi cấu hình quan trọng kèm dữ liệu trước/sau (oldValue/newValue).
 *
 * Các bảng liên quan:
 * - Bảng chính: AuditLog (*)
 * - Bảng tham chiếu: Hospital
 */
public interface IAuditService {

    /**
     * Ghi nhận một bản ghi nhật ký kiểm toán vào bảng AuditLog.
     * @param hospitalId
     * @param actorId
     * @param actorType
     * @param action
     * @param entityType
     * @param entityId
     * @param oldValue
     * @param newValue
     * @return AuditLog
     */
    AuditLog log(
            String hospitalId, // Có thể null nếu thao tác ở cấp toàn nền tảng (Super Admin / System)
            String actorId,    // ID người thực hiện hoặc "SYSTEM"
            String actorType,  // PATIENT | STAFF | PLATFORM_ADMIN | SYSTEM
            String action,     // CREATE | UPDATE | DELETE | CANCEL | SUSPEND...
            String entityType, // Tên thực thể bị tác động: Appointment, HospitalPolicy, ReceptionWindow...
            String entityId,   // ID bản ghi bị tác động
            String oldValue,   // Chuỗi JSON dữ liệu trước khi thay đổi (có thể null)
            String newValue    // Chuỗi JSON dữ liệu sau khi thay đổi (có thể null)
    );


    record AuditLog(
            Long id,
            String hospitalId,
            String actorId,
            String actorType,
            String action,
            String entityType,
            String entityId,
            String oldValue, // JSON string
            String newValue, // JSON string
            ZonedDateTime createdAt
    ) {}
}