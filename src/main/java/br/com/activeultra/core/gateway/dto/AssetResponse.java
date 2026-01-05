package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.util.EnumUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record AssetResponse(

        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        // Basic
        UUID id,
        String name,
        BigDecimal acquisitionValue,
        String code,
        String category,
        String status,
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

    public static AssetResponse fromEntity(Asset asset) {
        return AssetResponse.builder()
                .createdAt(asset.getCreatedAt())
                .updatedAt(asset.getUpdatedAt())
                .id(asset.getId())
                .name(asset.getName())
                .acquisitionValue(asset.getAcquisitionValue())
                .code(asset.getCode())
                .category(EnumUtil.toString(asset.getCategory()))
                .status(EnumUtil.toString(asset.getStatus()))
                .serialNumber(asset.getSerialNumber())
                .location(asset.getLocation())
                .acquisitionDate(asset.getAcquisitionDate())
                .expectedLifetimeMonths(asset.getExpectedLifetimeMonths())
                .brand(asset.getBrand())
                .model(asset.getModel())
                .modelYear(asset.getModelYear())
                .manufactureYear(asset.getManufactureYear())
                .licensePlate(asset.getLicensePlate())
                .chassisNumber(asset.getChassisNumber())
                .renavam(asset.getRenavam())
                .fleetNumber(asset.getFleetNumber())
                .color(asset.getColor())
                .vehicleType(EnumUtil.toString(asset.getAssetVehicleType()))
                .fuelType(EnumUtil.toString(asset.getAssetFuelType()))
                .transmissionType(EnumUtil.toString(asset.getAssetTransmissionType()))
                .ownershipType(EnumUtil.toString(asset.getAssetOwnershipType()))
                .odometerKm(asset.getOdometerKm())
                .seatingCapacity(asset.getSeatingCapacity())
                .axleCount(asset.getAxleCount())
                .maxLoadKg(asset.getMaxLoadKg())
                .engineDisplacementCc(asset.getEngineDisplacementCc())
                .tankCapacityLiters(asset.getTankCapacityLiters())
                .insuranceCompany(asset.getInsuranceCompany())
                .insurancePolicyNumber(asset.getInsurancePolicyNumber())
                .insuranceExpiryDate(asset.getInsuranceExpiryDate())
                .registrationExpiryDate(asset.getRegistrationExpiryDate())
                .currentDriver(asset.getCurrentDriver())
                .gpsTrackerId(asset.getGpsTrackerId())
                .notes(asset.getNotes())
                .build();
    }
}
