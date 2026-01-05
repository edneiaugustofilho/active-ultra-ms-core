package br.com.activeultra.core.entity;

import br.com.activeultra.core.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_asset",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_assets_tenant_code", columnNames = {"tenant_id", "code"})
        })
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends TenantEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "acquisition_value", precision = 15, scale = 2)
    private BigDecimal acquisitionValue;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private AssetCategory category;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(length = 150)
    private String location;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @Column(name = "expected_lifetime_months")
    private Integer expectedLifetimeMonths;

// ---------- IDENTIFICATION / BASIC DATA ----------

    @Column(name = "brand", length = 50)
    private String brand;           // e.g. Toyota, VW, Scania

    @Column(name = "model", length = 80)
    private String model;           // e.g. Corolla XEI 2.0

    @Column(name = "model_year")
    private Integer modelYear;      // ano-modelo

    @Column(name = "manufacture_year")
    private Integer manufactureYear; // ano-fabricação

    @Column(name = "license_plate", length = 20, unique = true)
    private String licensePlate;

    @Column(name = "chassis_number", length = 50, unique = true)
    private String chassisNumber;             // chassis number

// Brazil-specific (optional but super common)
    @Column(name = "renavam", length = 20, unique = true)
    private String renavam;

    @Column(name = "fleet_number", length = 30)
    private String fleetNumber;     // identificação interna da frota

    @Column(name = "color", length = 30)
    private String color;


// ---------- CLASSIFICATION ----------

    @Column(name = "vehicle_type", length = 30)
    @Enumerated(EnumType.STRING)
    private AssetVehicleType assetVehicleType;

    @Column(name = "fuel_type", length = 20)
    @Enumerated(EnumType.STRING)
    private AssetFuelType assetFuelType;

    @Column(name = "transmission_type", length = 20)
    @Enumerated(EnumType.STRING)
    private AssetTransmissionType assetTransmissionType;

    @Column(name = "ownership_type", length = 20)
    @Enumerated(EnumType.STRING)
    private AssetOwnershipType assetOwnershipType;

// ---------- TECHNICAL SPECS ----------

    @Column(name = "odometer_km")
    private Long odometerKm;        // última leitura conhecida

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(name = "axle_count")
    private Integer axleCount;

    @Column(name = "max_load_kg")
    private Integer maxLoadKg;      // carga máxima permitida

    @Column(name = "engine_displacement_cc")
    private Integer engineDisplacementCc;

    @Column(name = "tank_capacity_liters")
    private Integer tankCapacityLiters;

// ---------- MAINTENANCE / REGULATORY / OPERATION ----------

    @Column(name = "insurance_company", length = 100)
    private String insuranceCompany;

    @Column(name = "insurance_policy_number", length = 50)
    private String insurancePolicyNumber;

    @Column(name = "insurance_expiry_date")
    private LocalDate insuranceExpiryDate;

    @Column(name = "registration_expiry_date")
    private LocalDate registrationExpiryDate; // licenciamento

    @Column(name = "current_driver", length = 100)
    private String currentDriver;   // ou ref para usuário/responsável

    @Column(name = "gps_tracker_id", length = 50)
    private String gpsTrackerId;    // ID do rastreador/telemetria

    @Column(name = "notes", length = 500)
    private String notes;

}
