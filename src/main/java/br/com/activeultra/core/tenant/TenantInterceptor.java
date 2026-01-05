package br.com.activeultra.core.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private final TenantContext tenantContext;

    public TenantInterceptor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        String header = request.getHeader("X-Tenant-Id");

        if (header == null || header.isBlank()) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return false;
        }

        try {
            UUID tenantId = UUID.fromString(header);
            tenantContext.setTenantId(tenantId);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        tenantContext.clear();
    }
}
