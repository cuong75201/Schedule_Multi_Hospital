package com.carepath.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.carepath.entities.enums.EntityStatus;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fee_tier_config", indexes = {
        @Index(name = "idx_fee_tier_config_effective_period", columnList = "effective_from, effective_to"),
        @Index(name = "idx_fee_tier_config_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
public class FeeTierConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal minRevenue;

    @Column(precision = 19, scale = 2)
    private BigDecimal maxRevenue;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal feeRatePct;

    @Column(nullable = false)
    private LocalDateTime effectiveFrom;

    private LocalDateTime effectiveTo;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private EntityStatus status = EntityStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private PlatformAdminEntity createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
