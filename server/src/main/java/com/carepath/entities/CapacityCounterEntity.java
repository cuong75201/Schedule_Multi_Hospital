package com.carepath.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "capacity_counter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CapacityCounterEntity {
    @Id
    @Column(name = "window_id")
    private UUID windowId;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "window_id", nullable = false)
    private ReceptionWindowEntity window;

    @Column(nullable = false)
    @Builder.Default
    private int bookedCount = 0;

    @Version
    @Column(nullable = false)
    @Builder.Default
    private int version = 0;

    public CapacityCounterEntity(ReceptionWindowEntity window) {
        this.window = window;
    }
}
