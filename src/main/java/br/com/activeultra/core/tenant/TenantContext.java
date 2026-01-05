package br.com.activeultra.core.tenant;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TenantContext {

    private static final ThreadLocal<UUID> CURRENT_TENANT = new ThreadLocal<>();

    public void setTenantId(UUID tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public Optional<UUID> getTenantId() {
        return Optional.ofNullable(CURRENT_TENANT.get());
    }

    public void clear() {
        CURRENT_TENANT.remove();
    }
}
