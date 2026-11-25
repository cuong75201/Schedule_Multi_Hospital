package com.carepath.contracts;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.jspecify.annotations.Nullable;

/**
 * Class: BillingFeeService
 * Tables: MonthlyInvoice, FeeTierConfig, DebtAlertConfig,
 * WalletTransaction, HospitalWallet, Payment
 */
public interface IBillingFeeService {

    public record FeeTierConfig(String code, double minRevenue, double maxRevenue, double feeRatePct,
            String effectiveFrom, String effectiveTo, String createdById) {
    }

    /**
     * 
     * yêu cầu: không được có gap và overlay cái được tạo phải nối tiếp với cái
     * trước thì khi minRevenue = 0
     * 
     * @param code
     * @param minRevenue
     * @param maxRevenue
     * @param feeRatePct
     * @param effectiveFrom
     * @param effectiveTo
     * @param createdById
     * @return
     */
    FeeTierConfig upsertFeeTierConfig(
            String code, double minRevenue, double maxRevenue, double feeRatePct,
            String effectiveFrom, String effectiveTo, String createdById);

    ArrayList<FeeTierConfig> listFeeTierConfigs();

    public record DebtAlertConfig(double thresholdAmount, int thresholdDays, String actionOnExceed,
            String createdById) {
    }

    /**
     * 
     * không xóa cái cũ, chỉ thêm cái mới, để lịch sử cập nhật lại status
     * 
     * @param thresholdAmount
     * @param thresholdDays
     * @param actionOnExceed
     * @param createdById
     * @return
     */
    DebtAlertConfig upsertDebtAlertConfig(
            double thresholdAmount, int thresholdDays, String actionOnExceed, String createdById);

    DebtAlertConfig getDebtAlertConfig();

    /**
     * 
     * lấy fee tier config theo totalRevenue và asOf, nếu không có thì trả về null
     * 
     * @param totalRevenue tổng doanh thu của bệnh viện trong tháng
     * @param asOf         ngày tính toán, để xác định fee tier config nào đang có
     *                     hiệu lực
     * @return
     */
    FeeTierConfig resolveFeeTier(double totalRevenue, ZonedDateTime asOf);

    public record MonthlyRevenue(double online, double counter, double total) {
    }

    MonthlyRevenue calculateMonthlyRevenue(String hospitalId, int year, int month);

    public enum GeneratedByType {
        SYSTEM_JOB, PLATFORM_ADMIN
    }

    public record MonthlyInvoice(
            String id, String hospitalId, int periodYear, int periodMonth, double onlineRevenue,
            double counterRevenue, double totalRevenue, double feeRatePct, String feeTierCode,
            double feeAmount, String status, String dueDate, String paidAt, boolean paidFromWallet,
            String walletTransactionId, double debtAmount, GeneratedByType generatedByType,
            @Nullable String generatedById) {
    }

    /***
     * [Scheduler / Cron job cuối tháng]
     * → BillingFeeService.generateMonthlyInvoice(hospitalId, year, month)
     * ├─ calculateMonthlyRevenue(...) // ra totalRevenue
     * ├─ resolveFeeTier(totalRevenue, asOf) // ← GỌI Ở ĐÂY
     * ├─ feeAmount = totalRevenue × tier.feeRatePct / 100
     * └─ INSERT MonthlyInvoice (snapshot tier)
     * MonthlyRevenue
     * 
     * @param online
     * @param counter
     * @param total
     */
    MonthlyInvoice generateMonthlyInvoice(String hospitalId, int year, int month,
            GeneratedByType generatedByType, @Nullable String generatedById);

    /**
     * 
     * Hiện tôi chưa có cách nào nên có gì để admin gọi
     * 
     * @param invoiceId
     * @return
     */
    MonthlyInvoice payInvoiceFromWallet(String invoiceId);

    /**
     * 
     * system job sẽ gọi hàm này để đánh dấu các hóa đơn quá hạn
     * 
     * @param invoiceId
     * @return
     */
    MonthlyInvoice markInvoiceOverdue(String invoiceId);

    /**
     * 
     * DebtThresholdCheckResult
     * 
     * @param exceeded có vượt quá threshold không
     * @param action  Hệ thống làm gì Ảnh hưởng BV
     *               WARN_ONLY Gửi cảnh báo tới Admin BV (qua NotificationOutbox) Chỉ nhắc nhở
     *               LOCK_WITHDRAWAL HospitalWallet.status = WITHDRAW_LOCKED Không rút được tiền từ ví (BR-1903)
     *               SUSPEND_BOOKING Chặn booking mới (set cờ) Không nhận lịch mới, nhưng lịch cũ vẫn giữ
     * @return
     */
    public record DebtThresholdCheckResult(boolean exceeded, String action) {
    }

    /**
     * 
     * system job sẽ gọi hàm này để check xem bệnh viện có vượt quá threshold không
     * 
     * @param hospitalId
     * @return
     */
    DebtThresholdCheckResult checkDebtThreshold(String hospitalId);

    void applyDebtAction(String hospitalId, String action);

    ArrayList<MonthlyInvoice> listInvoices(String hospitalId);

    ArrayList<MonthlyInvoice> listAllInvoices(String platformAdminId);
}
