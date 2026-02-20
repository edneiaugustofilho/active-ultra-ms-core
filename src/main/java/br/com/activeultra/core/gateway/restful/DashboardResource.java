package br.com.activeultra.core.gateway.restful;

import br.com.activeultra.core.gateway.dto.DashboardResponse;
import br.com.activeultra.core.service.DashboardBuildService;
import br.com.activeultra.core.service.DashboardGetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardResource {

    private final DashboardBuildService dashboardBuildService;
    private final DashboardGetService dashboardGetService;

    public DashboardResource(DashboardBuildService dashboardBuildService,
                             DashboardGetService dashboardGetService) {
        this.dashboardBuildService = dashboardBuildService;
        this.dashboardGetService = dashboardGetService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> get() {
        return ResponseEntity.ok(DashboardResponse.fromEntity(dashboardGetService.execute()));
    }

    @GetMapping("/build")
    public ResponseEntity<DashboardResponse> build() {
        return ResponseEntity.ok(DashboardResponse.fromEntity(dashboardBuildService.execute()));
    }
}
