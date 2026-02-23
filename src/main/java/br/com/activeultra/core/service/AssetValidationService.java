package br.com.activeultra.core.service;

import br.com.activeultra.core.enums.AssetTransmissionType;
import br.com.activeultra.core.enums.AssetCategory;
import br.com.activeultra.core.enums.AssetFuelType;
import br.com.activeultra.core.enums.AssetOwnershipType;
import br.com.activeultra.core.enums.AssetVehicleType;
import br.com.activeultra.core.exceptions.FieldValidationException;
import br.com.activeultra.core.gateway.dto.AssetUpsertRequest;
import br.com.activeultra.core.repository.AssetRepository;
import br.com.activeultra.core.util.DateUtil;
import br.com.activeultra.core.util.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetValidationService {

    private final AssetRepository assetRepository;
    private final TenantService tenantService;

    public void validate(AssetUpsertRequest request) {
        validate(request, null);
    }

    public void validate(AssetUpsertRequest request, UUID assetId) {
        List<String> errors = new ArrayList<>();

        // ---------- REQUIRED FIELDS ----------

        if (isBlank(request.name())) {
            errors.add("name is required.");
        }

        if (isBlank(request.code())) {
            errors.add("code is required.");
        }

        // ---------- STRING LENGTH CONSTRAINTS (from DDL / @Column) ----------
        validateLength("name", request.name(), 100, errors);
        validateLength("code", request.code(), 50, errors);
        validateLength("category", request.category(), 30, errors);
        validateLength("serialNumber", request.serialNumber(), 100, errors);
        validateLength("location", request.location(), 150, errors);

        validateLength("brand", request.brand(), 50, errors);
        validateLength("model", request.model(), 80, errors);
        validateLength("licensePlate", request.licensePlate(), 20, errors);
        validateLength("chassisNumber", request.chassisNumber(), 50, errors);
        validateLength("renavam", request.renavam(), 20, errors);
        validateLength("fleetNumber", request.fleetNumber(), 30, errors);
        validateLength("color", request.color(), 30, errors);

        validateLength("vehicleType", request.vehicleType(), 30, errors);
        validateLength("fuelType", request.fuelType(), 20, errors);
        validateLength("transmissionType", request.transmissionType(), 20, errors);
        validateLength("ownershipType", request.ownershipType(), 20, errors);

        validateLength("insuranceCompany", request.insuranceCompany(), 100, errors);
        validateLength("insurancePolicyNumber", request.insurancePolicyNumber(), 50, errors);
        validateLength("currentDriver", request.currentDriver(), 100, errors);
        validateLength("gpsTrackerId", request.gpsTrackerId(), 50, errors);
        validateLength("notes", request.notes(), 500, errors);

        // ---------- NUMERIC / RANGE VALIDATIONS (basic sanity checks) ----------
        if (request.expectedLifetimeMonths() != null && request.expectedLifetimeMonths() < 0) {
            errors.add("expectedLifetimeMonths cannot be negative.");
        }

        if (request.odometerKm() != null && request.odometerKm() < 0) {
            errors.add("odometerKm cannot be negative.");
        }

        if (request.seatingCapacity() != null && request.seatingCapacity() < 0) {
            errors.add("seatingCapacity cannot be negative.");
        }

        if (request.axleCount() != null && request.axleCount() < 0) {
            errors.add("axleCount cannot be negative.");
        }

        if (request.maxLoadKg() != null && request.maxLoadKg() < 0) {
            errors.add("maxLoadKg cannot be negative.");
        }

        if (request.engineDisplacementCc() != null && request.engineDisplacementCc() < 0) {
            errors.add("engineDisplacementCc cannot be negative.");
        }

        if (request.tankCapacityLiters() != null && request.tankCapacityLiters() < 0) {
            errors.add("tankCapacityLiters cannot be negative.");
        }

        // Years (simple plausibility check)
        if (request.modelYear() != null && (request.modelYear() < 1900 || request.modelYear() > 2100)) {
            errors.add("modelYear must be between 1900 and 2100.");
        }

        if (request.manufactureYear() != null && (request.manufactureYear() < 1900 || request.manufactureYear() > 2100)) {
            errors.add("manufactureYear must be between 1900 and 2100.");
        }

        // ---------- ENUM VALIDATIONS ----------
        EnumUtil.validateEnum("category", AssetCategory.class, request.category(), errors);
        EnumUtil.validateEnum("vehicleType", AssetVehicleType.class, request.vehicleType(), errors);
        EnumUtil.validateEnum("fuelType", AssetFuelType.class, request.fuelType(), errors);
        EnumUtil.validateEnum("transmissionType", AssetTransmissionType.class, request.transmissionType(), errors);
        EnumUtil.validateEnum("ownershipType", AssetOwnershipType.class, request.ownershipType(), errors);

        // Logical date checks (only if successfully parsed)
        if (request.acquisitionDate() != null && request.insuranceExpiryDate() != null
                && request.insuranceExpiryDate().isBefore(request.acquisitionDate())) {
            errors.add("insuranceExpiryDate cannot be before acquisitionDate.");
        }

        if (request.acquisitionDate() != null && request.registrationExpiryDate() != null
                && request.registrationExpiryDate().isBefore(request.acquisitionDate())) {
            errors.add("registrationExpiryDate cannot be before acquisitionDate.");
        }

        // ---------- UNIQUE CONSTRAINT (tenant_id, code) ----------
        if (!isBlank(request.code())) {
            UUID tenantId = tenantService.getTenantId();

            if (tenantId == null) {
                throw new IllegalStateException("Tenant ID not found.");
            }

            boolean exists = (assetId != null)
                    ? assetRepository.existsByTenantIdAndCodeIgnoreCaseAndIdNot(tenantId, request.code(), assetId)
                    : assetRepository.existsByTenantIdAndCodeIgnoreCase(tenantId, request.code());

            if (exists) {
                errors.add("An asset with code '" + request.code() + "' already exists for this tenant.");
            }
        }

        // ---------- FINAL DECISION ----------
        if (!errors.isEmpty()) {
            throw new FieldValidationException(errors);
        }
    }

    // ===== Helpers =====

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void validateLength(String fieldName, String value, int maxLength, List<String> errors) {
        if (value != null && value.length() > maxLength) {
            errors.add(String.format("%s length must be at most %d characters.", fieldName, maxLength));
        }
    }

}
