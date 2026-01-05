package br.com.activeultra.core.service;

import br.com.activeultra.core.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TenantService {

    private final TenantContext tenantContext;

    public TenantService(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    protected UUID getTenantId() {
        return tenantContext.getTenantId().orElseThrow(() -> new IllegalStateException("Tenant not found"));
    }

}
