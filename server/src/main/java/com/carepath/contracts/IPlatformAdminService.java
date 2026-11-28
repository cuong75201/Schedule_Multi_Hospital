package com.carepath.contracts;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * IPlatformAdminService: Quản lý tài khoản quản trị viên nền tảng (Super Admin)
 * và quyền giám sát tổng thể ví, phí, công nợ của toàn bộ các bệnh viện trên hệ thống.
 *
 * Các bảng liên quan:
 * - Bảng chính: PlatformAdmin (*)
 * - Bảng tham chiếu & cấu hình: FeeTierConfig, DebtAlertConfig, HospitalWallet, MonthlyInvoice
 */
public interface IPlatformAdminService {

    /**
     * Tạo mới tài khoản quản trị viên nền tảng (Super Admin).
     * @param username
     * @param passwordHash
     * @param fullName
     * @param email
     * @return PlatformAdmin
     */
    PlatformAdmin createAdmin(
            String username,
            String passwordHash,
            String fullName,
            String email // Có thể null
    );

    /**
     * Xác thực đăng nhập quản trị viên nền tảng (kiểm tra username, passwordHash và trạng thái ACTIVE).
     * @param username
     * @param passwordHash
     * @return PlatformAdmin
     */
    PlatformAdmin authenticate(
            String username,
            String passwordHash
    );

    /**
     * Xem danh sách toàn bộ ví của tất cả bệnh viện trên hệ thống.
     * @return List<HospitalWallet>
     */
    List<HospitalWallet> viewAllWallets();

    /**
     * Xem danh sách toàn bộ hóa đơn phí tháng / công nợ của tất cả bệnh viện trên hệ thống
     * @return List<MonthlyInvoice>
     */
    List<MonthlyInvoice> viewAllDebts();


    record PlatformAdmin(
            String id,
            String username,
            String fullName,
            String email,
            String status, // ACTIVE | INACTIVE | SUSPENDED
            ZonedDateTime createdAt
    ) {}

    record HospitalWallet(
            String id,
            String hospitalId,
            BigDecimal balance,
            String currency,
            String status, // ACTIVE | WITHDRAW_LOCKED | CLOSED
            int version,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
    ) {}

    record MonthlyInvoice(
            String id,
            String hospitalId,
            int periodYear,
            int periodMonth,
            BigDecimal onlineRevenue,
            BigDecimal counterRevenue,
            BigDecimal totalRevenue,
            BigDecimal feeRatePct,
            String feeTierCode,
            String feeTierConfigId,
            BigDecimal feeAmount,
            String status, // DRAFT | ISSUED | PAID | OVERDUE | VOID
            ZonedDateTime dueDate,
            ZonedDateTime paidAt,
            boolean paidFromWallet,
            String walletTransactionId,
            BigDecimal debtAmount,
            String generatedByType,
            String generatedById,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
    ) {}
}