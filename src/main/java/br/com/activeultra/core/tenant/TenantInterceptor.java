package br.com.activeultra.core.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.UUID;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-Id";

    private static final List<String> SKIP_TENANT_PATHS = List.of(
            "/api/health",
            "/api/public/**",
            "/core/api/health",
            "/core/api/public/**"
    );

    private final TenantContext tenantContext;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public TenantInterceptor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String path = request.getRequestURI();
        if (shouldSkipTenant(path)) {
            return true;
        }

        String header = request.getHeader(TENANT_HEADER);
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

    private boolean shouldSkipTenant(String requestUri) {
        for (String pattern : SKIP_TENANT_PATHS) {
            if (pathMatcher.match(pattern, requestUri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        tenantContext.clear();
    }
}
