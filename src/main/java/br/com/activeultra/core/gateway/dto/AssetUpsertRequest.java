package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetTransmissionType;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetFuelType;
import br.com.activeultra.core.enums.AssetOwnershipType;
import br.com.activeultra.core.enums.AssetVehicleType;
import br.com.activeultra.core.util.EnumUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssetUpsertRequest(

        // Basic
        String name,
        BigDecimal acquisitionValue,
        String code,
        String category,             // AssetCategory
        String serialNumber,
        String location,
        LocalDate acquisitionDate,
        Integer expectedLifetimeMonths,

        // Identification / Basic data
        String brand,
        String model,
        Integer modelYear,
        Integer manufactureYear,
        String licensePlate,
        String chassisNumber,
        String renavam,
        String fleetNumber,
        String color,

        // Classification
        String vehicleType,          // VehicleType
        String fuelType,             // FuelType
        String transmissionType,     // TransmissionType
        String ownershipType,        // OwnershipType

        // Technical specs
        Long odometerKm,
        Integer seatingCapacity,
        Integer axleCount,
        Integer maxLoadKg,
        Integer engineDisplacementCc,
        Integer tankCapacityLiters,

        // Maintenance / regulatory / operation
        String insuranceCompany,
        String insurancePolicyNumber,
        LocalDate insuranceExpiryDate,
        LocalDate registrationExpiryDate,
        String currentDriver,
        String gpsTrackerId,
        String notes
) {

    public Asset toEntity() {
        return Asset.builder()
                // Basic
                .name(name())
                .code(code())
                .acquisitionValue(acquisitionValue())
                .category(EnumUtil.parseEnum(AssetCategory.class, category()))
                .serialNumber(serialNumber())
                .location(location())
                .acquisitionDate(acquisitionDate())
                .expectedLifetimeMonths(expectedLifetimeMonths())

                // Identification / Basic data
                .brand(brand())
                .model(model())
                .modelYear(modelYear())
                .manufactureYear(manufactureYear())
                .licensePlate(licensePlate())
                .chassisNumber(chassisNumber())
                .renavam(renavam())
                .fleetNumber(fleetNumber())
                .color(color())

                // Classification
                .assetVehicleType(EnumUtil.parseEnum(AssetVehicleType.class, vehicleType()))
                .assetFuelType(EnumUtil.parseEnum(AssetFuelType.class, fuelType()))
                .assetTransmissionType(EnumUtil.parseEnum(AssetTransmissionType.class, transmissionType()))
                .assetOwnershipType(EnumUtil.parseEnum(AssetOwnershipType.class, ownershipType()))

                // Technical specs
                .odometerKm(odometerKm())
                .seatingCapacity(seatingCapacity())
                .axleCount(axleCount())
                .maxLoadKg(maxLoadKg())
                .engineDisplacementCc(engineDisplacementCc())
                .tankCapacityLiters(tankCapacityLiters())

                // Maintenance / regulatory / operation
                .insuranceCompany(insuranceCompany())
                .insurancePolicyNumber(insurancePolicyNumber())
                .insuranceExpiryDate(insuranceExpiryDate())
                .registrationExpiryDate(registrationExpiryDate())
                .currentDriver(currentDriver())
                .gpsTrackerId(gpsTrackerId())
                .notes(notes())

                .build();
    }
}
