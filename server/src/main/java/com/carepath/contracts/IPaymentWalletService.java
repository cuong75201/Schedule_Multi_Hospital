package com.carepath.contracts;

import java.time.ZonedDateTime;

/**
 * 
 * IPaymentWalletService
 * Tables: Payment, PaymentWebhookEvent, HospitalWallet, WalletTransaction
 * 
 * NOTES:  <br>
 * - lúc người dùng đặt lịch xong thì sẽ có nút là thanh toán online nếu mà bấm
 * nút đó thì sẽ gọi createOnlinePayment
 * nếu không thì lúc mà người dùng đến quầy để checkin xong thì hệ thống sẽ
 * gọi createCounterPayment để ghi nhận là bệnh nhân đã thanh toán tiền mặt tại
 * quầy
 * nếu bệnh nhân không đến thì không gọi CreateCounterPayment
 */
public interface IPaymentWalletService {

    public record Payment(
            String id, String satus, String channel, String method, String patientId, String appointmentId,
            double amount, String gatewayTxnId, ZonedDateTime paidAt) {
    }

    /**
     * Tạo bản ghi thanh toán PENDING khi bệnh nhân bấm "Thanh toán online" — trước
     * khi cổng thanh toán xác nhận. Đây là bước khởi tạo, chưa phải tiền thật.
     * 
     * Tạo Payment với:
     * channel = ONLINE
     * method = ONLINE_GATEWAY
     * status = PENDING
     * gatewayProvider null
     * appointmentId, hospitalId, patientId, amount
     * 
     * 
     * @param appointmentId
     * @param amount
     * @return
     */
    Payment createOnlinePayment(String appointmentId, double amount);

    /**
     * Ghi nhận bệnh nhân đã trả tiền mặt tại quầy khi check-in. Đây là bản ghi kế
     * toán nội bộ để cuối tháng tính doanh thu — tiền không đi qua nền tảng.
     * 
     * @param appointmentId
     * @param staffId
     * @param amount
     * @return Payment với: <br>
     *         status = SUCCESS (vì thu tiền mặt là xong ngay) <br>
     *         channel = COUNTER <br>
     *         method = CASH_AT_COUNTER <br>
     *         paidAt = now() <br>
     *         collectedById = staffId <br>
     *         KHÔNG sinh WalletTransaction
     */
    Payment createCounterPayment(String appointmentId, String staffId, double amount);

    public record PaymentWebhookEvent(String id, String provider, String eventId, String rawPayload,
            String status) {
    }

    PaymentWebhookEvent handleGatewayWebhook(String provider, String eventId, String rawPayload);

    public record WalletTransaction(String id, String hospitalId, String type, double amount, String refType,
            String refId, ZonedDateTime createdAt) {
    }

    WalletTransaction creditWallet(String hospitalId, String type, double amount, String refType, String refId);

    public record WithdrawalRequest(String id, String hospitalId, String staffId, double amount, String bankInfo,
            String status, ZonedDateTime requestedAt, ZonedDateTime approvedAt, ZonedDateTime paidAt) {
    }

    WithdrawalRequest requestWithdrawal(String hospitalId, String staffId, double amount, String bankInfo);

    WithdrawalRequest approveWithdrawal(String withdrawalId, String platformAdminId);

    public record HospitalWallet(String hospitalId, double balance, String status) {
    }

    HospitalWallet getWallet(String hospitalId);

    public record WalletTransactionFilter(String type, ZonedDateTime from, ZonedDateTime to) {
    }

    WalletTransaction[] listTransactions(String hospitalId, WalletTransactionFilter filter);

    HospitalWallet[] getAllWallets(String platformAdminId);

}
