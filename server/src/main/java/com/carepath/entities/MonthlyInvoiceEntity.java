package com.carepath.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.carepath.entities.enums.InvoiceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "monthly_invoice", uniqueConstraints = {
        @UniqueConstraint(name = "uk_monthly_invoice_hospital_period", columnNames = { "hospital_id", "period_year", "period_month" })
}, indexes = {
        @Index(name = "idx_monthly_invoice_status_due_date", columnList = "status, due_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MonthlyInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @Column(nullable = false)
    private int periodYear;

    @Column(nullable = false)
    private int periodMonth;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal onlineRevenue = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal counterRevenue = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalRevenue;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal feeRatePct;

    private String feeTierCode;

    @ManyToOne
    @JoinColumn(name = "fee_tier_config_id")
    private FeeTierConfigEntity feeTierConfig;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal feeAmount;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    @Builder.Default
    private InvoiceStatus status = InvoiceStatus.DRAFT;

    private LocalDateTime dueDate;

    private LocalDateTime paidAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean paidFromWallet = false;

    @ManyToOne
    @JoinColumn(name = "wallet_transaction_id")
    private WalletTransactionEntity walletTransaction;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal debtAmount = BigDecimal.ZERO;

    @Column(name = "generated_by_type")
    private String generatedByType;

    @ManyToOne
    @JoinColumn(name = "generated_by_id")
    private PlatformAdminEntity generatedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
