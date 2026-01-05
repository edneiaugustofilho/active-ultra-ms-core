package br.com.activeultra.core.mapper;

import br.com.activeultra.core.entity.Asset;
import br.com.activeultra.core.enums.AssetTransmissionType;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetFuelType;
import br.com.activeultra.core.enums.AssetOwnershipType;
import br.com.activeultra.core.enums.AssetVehicleType;
import br.com.activeultra.core.gateway.dto.AssetUpsertRequest;
import br.com.activeultra.core.util.EnumUtil;

public class AssetMapper {

    public static void upsertIntoEntity(AssetUpsertRequest request, Asset entity) {
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

}
