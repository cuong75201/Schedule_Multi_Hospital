package com.carepath.contracts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * IHospitalPolicyService: Quản lý cấu hình chính sách đặt lịch, hủy lịch, check-in,
 * overbooking và no-show riêng biệt cho từng bệnh viện (hoặc ghi đè theo từng dịch vụ).
 *
 * Các Rule tương ứng: BR-CORE-09 (cùng các rule vận hành liên đới: BR-602..605, BR-805, BR-1002, BR-1101..1102, BR-1502)
 * - BR-CORE-09: Mỗi bệnh viện có thể có chính sách đặt/hủy/đổi lịch riêng, không ép dùng chung một policy cứng.
 *
 * Các bảng liên quan:
 * - Bảng chính: HospitalPolicy (*)
 * - Bảng tham chiếu & log: Hospital, Service, AuditLog
 */
public interface IHospitalPolicyService {

    /**
     * Khởi tạo hoặc cập nhật chính sách cho bệnh viện, dựa vào serviceId và hospitalId để quyết định (không quan tâm serviceId = null hay != null)
     * 1. Nếu hospitalId và serviceId đã tồn tại -> cập nhật/ghi đè
     * 2. Nếu hospitalId và serviceId chưa tồn tại -> tạo bảng ghi mới
     * Ghi nhận thay đổi cấu hình vào AuditLog.
     * Giải quyết: BR-CORE-09, BR-1502
     * priorityPolicyEnabled mặc định false (quyết định bỏ)
     */
    HospitalPolicy upsertPolicy(
            String hospitalId,
            String serviceId,
            int bookingAdvanceDays,
            boolean allowOverbooking,
            BigDecimal overbookingLimitPct,
            boolean preventDuplicateBooking,
            int checkinEarlyMin,
            int checkinLateMin,
            int cancelCutoffMin,
            boolean allowSelfCancelAfterCutoff,
            int noShowAfterMin,
            boolean priorityPolicyEnabled 
    );

    /**
     * Lấy chính sách đang có hiệu lực thực tế cho một dịch vụ tại bệnh viện theo cơ chế Fallback:
     * 1. Tìm bản ghi có (hospitalId, serviceId).
     * 2. Nếu không có cấu hình riêng cho dịch vụ, trả về bản ghi mặc định (hospitalId, serviceId = null).
     * Giải quyết: BR-CORE-09
     */
    HospitalPolicy getEffectivePolicy(
            String hospitalId,
            String serviceId
    );

    /**
     * Lấy toàn bộ danh sách chính sách của một bệnh viện (bao gồm policy mặc định và các policy theo từng dịch vụ)
     * để hiển thị trên trang quản trị của Admin bệnh viện.
     * Giải quyết: BR-CORE-09, BR-1502
     */
    List<HospitalPolicy> getAllPoliciesByHospital(
            String hospitalId
    );

    /**
     * Xóa chính sách ghi đè của một dịch vụ để dịch vụ đó quay về áp dụng chính sách mặc định của bệnh viện.
     * (Không cho phép xóa chính sách mặc định của bệnh viện khi serviceId = null).
     * Giải quyết: BR-CORE-09
     */
    void deleteServicePolicyOverride(
            String hospitalId,
            String serviceId
    );

    /**
     * Hàm kiểm tra quy tắc thời gian động (Read-only Validation), KHÔNG thêm/sửa/xóa dữ liệu trong database.
     *
     * Mục đích:
     * - Tập trung toàn bộ logic so sánh/kiểm tra chính sách mỗi khi có người gọi. Kết quả trả về cho phép hay từ chối
     *
     * Ai gọi và dùng khi nào? Được gọi nội bộ ở bước kiểm tra đầu vào bởi các Service nghiệp vụ khác trước khi thay đổi dữ liệu
     * 
     * - Ví dụ: Khi bệnh nhân đăng ký lịch (actionType = "BOOK"), thay vì gọi từng method kiểm tra, chỉ cần gọi
     * method này kiểm tra một lần duy nhất -> Kết quả trả về cho phép (Nếu thỏa các điều kiện chính sách), 
     * ngược lại (Nếu đăng ký quá sớm so với thời gian cho phép)
     *
     * Giải quyết: BR-CORE-09 (hỗ trợ kiểm tra động cho BR-602, BR-1002, BR-1101, BR-1102)
     */
    PolicyCheckResult validatePolicyRule(
            String hospitalId,
            String serviceId,
            String actionType, // BOOK | CANCEL | CHECK_IN
            LocalDateTime appointmentStartTime,
            LocalDateTime actionTime
    );

    // =========================================================================
    // RESPONSE DTOs
    // =========================================================================

    record HospitalPolicy(
            String id,
            String hospitalId,
            String serviceId,
            boolean isDefaultPolicy, // true nếu serviceId == null
            int bookingAdvanceDays,
            boolean allowOverbooking,
            BigDecimal overbookingLimitPct,
            boolean preventDuplicateBooking,
            int checkinEarlyMin,
            int checkinLateMin,
            int cancelCutoffMin,
            boolean allowSelfCancelAfterCutoff,
            int noShowAfterMin,
            boolean priorityPolicyEnabled,
            LocalDateTime createdAt
    ) {}

    record PolicyCheckResult(
            boolean allowed,
            String violatedRuleCode, // VD: CANCEL_CUTOFF_EXCEEDED, CHECKIN_TOO_EARLY, BOOKING_TOO_FAR...
            String message
    ) {}
}