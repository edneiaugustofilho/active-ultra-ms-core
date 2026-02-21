package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.*;
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

    public static Asset upsertToEntity(AssetUpsertRequest request) {
        Asset asset = new Asset();
        upsertToEntity(request, asset);
        return asset;
    }

    public static void upsertToEntity(AssetUpsertRequest request, Asset entity) {
        entity.setName(request.name());
        entity.setAcquisitionValue(request.acquisitionValue());
        entity.setCode(request.code());
        entity.setSerialNumber(request.serialNumber());
        entity.setCategory(EnumUtil.parseEnum(AssetCategory.class, request.category()));
        entity.setLocation(request.location());
        entity.setAcquisitionDate(request.acquisitionDate());
        entity.setExpectedLifetimeMonths(request.expectedLifetimeMonths());
        entity.setBrand(request.brand());
        entity.setModel(request.model());
        entity.setModelYear(request.modelYear());
        entity.setManufactureYear(request.manufactureYear());
        entity.setLicensePlate(request.licensePlate());
        entity.setChassisNumber(request.chassisNumber());
        entity.setRenavam(request.renavam());
        entity.setFleetNumber(request.fleetNumber());
        entity.setColor(request.color());
        entity.setAssetVehicleType(EnumUtil.parseEnum(AssetVehicleType.class, request.vehicleType()));
        entity.setAssetFuelType(EnumUtil.parseEnum(AssetFuelType.class, request.fuelType()));
        entity.setAssetTransmissionType(EnumUtil.parseEnum(AssetTransmissionType.class, request.transmissionType()));
        entity.setAssetOwnershipType(EnumUtil.parseEnum(AssetOwnershipType.class, request.ownershipType()));
        entity.setOdometerKm(request.odometerKm());
        entity.setSeatingCapacity(request.seatingCapacity());
        entity.setAxleCount(request.axleCount());
        entity.setMaxLoadKg(request.maxLoadKg());
        entity.setEngineDisplacementCc(request.engineDisplacementCc());
        entity.setTankCapacityLiters(request.tankCapacityLiters());
        entity.setInsuranceCompany(request.insuranceCompany());
        entity.setInsurancePolicyNumber(request.insurancePolicyNumber());
        entity.setInsuranceExpiryDate(request.insuranceExpiryDate());
        entity.setRegistrationExpiryDate(request.registrationExpiryDate());
        entity.setCurrentDriver(request.currentDriver());
        entity.setGpsTrackerId(request.gpsTrackerId());
        entity.setNotes(request.notes());

    }

    public static void upsertToEntityButLockedFields(AssetUpsertRequest request, Asset entity) {
        entity.setName(request.name());
        entity.setAcquisitionValue(request.acquisitionValue());
        entity.setCode(request.code());
        entity.setSerialNumber(request.serialNumber());
        entity.setAcquisitionDate(request.acquisitionDate());
        entity.setExpectedLifetimeMonths(request.expectedLifetimeMonths());
        entity.setBrand(request.brand());
        entity.setModel(request.model());
        entity.setModelYear(request.modelYear());
        entity.setManufactureYear(request.manufactureYear());
        entity.setLicensePlate(request.licensePlate());
        entity.setChassisNumber(request.chassisNumber());
        entity.setRenavam(request.renavam());
        entity.setFleetNumber(request.fleetNumber());
        entity.setColor(request.color());
        entity.setAssetVehicleType(EnumUtil.parseEnum(AssetVehicleType.class, request.vehicleType()));
        entity.setAssetFuelType(EnumUtil.parseEnum(AssetFuelType.class, request.fuelType()));
        entity.setAssetTransmissionType(EnumUtil.parseEnum(AssetTransmissionType.class, request.transmissionType()));
        entity.setAssetOwnershipType(EnumUtil.parseEnum(AssetOwnershipType.class, request.ownershipType()));
        entity.setOdometerKm(request.odometerKm());
        entity.setSeatingCapacity(request.seatingCapacity());
        entity.setAxleCount(request.axleCount());
        entity.setMaxLoadKg(request.maxLoadKg());
        entity.setEngineDisplacementCc(request.engineDisplacementCc());
        entity.setTankCapacityLiters(request.tankCapacityLiters());
        entity.setInsuranceCompany(request.insuranceCompany());
        entity.setInsurancePolicyNumber(request.insurancePolicyNumber());
        entity.setInsuranceExpiryDate(request.insuranceExpiryDate());
        entity.setRegistrationExpiryDate(request.registrationExpiryDate());
        entity.setGpsTrackerId(request.gpsTrackerId());
        entity.setNotes(request.notes());
    }
}
