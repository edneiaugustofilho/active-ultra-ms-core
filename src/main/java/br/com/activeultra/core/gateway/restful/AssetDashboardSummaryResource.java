package br.com.activeultra.core.gateway.restful;

import br.com.activeultra.core.gateway.dto.AssetDashboardSummaryResponse;
import br.com.activeultra.core.service.AssetDashboardSummaryBuildService;
import br.com.activeultra.core.service.AssetDashboardSummaryGetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asset-dashboard-summary")
public class AssetDashboardSummaryResource {

    private final AssetDashboardSummaryBuildService assetDashboardSummaryBuildService;
    private final AssetDashboardSummaryGetService assetDashboardSummaryGetService;

    public AssetDashboardSummaryResource(AssetDashboardSummaryBuildService assetDashboardSummaryBuildService,
                                         AssetDashboardSummaryGetService assetDashboardSummaryGetService) {
        this.assetDashboardSummaryBuildService = assetDashboardSummaryBuildService;
        this.assetDashboardSummaryGetService = assetDashboardSummaryGetService;
    }

    @GetMapping
    public ResponseEntity<AssetDashboardSummaryResponse> get() {
        return ResponseEntity.ok(AssetDashboardSummaryResponse.fromEntity(assetDashboardSummaryGetService.execute()));
    }

    @GetMapping("/build")
    public ResponseEntity<AssetDashboardSummaryResponse> build() {
        return ResponseEntity.ok(AssetDashboardSummaryResponse.fromEntity(assetDashboardSummaryBuildService.execute()));
    }
}
