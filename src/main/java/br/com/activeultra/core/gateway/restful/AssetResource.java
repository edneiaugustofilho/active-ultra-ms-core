package br.com.activeultra.core.gateway.restful;

import br.com.activeultra.core.gateway.dto.*;
import br.com.activeultra.core.service.AssetSearchService;
import br.com.activeultra.core.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetResource {

    private final AssetService assetService;
    private final AssetSearchService assetSearchService;

    public AssetResource(AssetService assetService,
                         AssetSearchService assetSearchService) {
        this.assetService = assetService;
        this.assetSearchService = assetSearchService;
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<AssetResumeDto>> search(@RequestBody AssetSearchRequest request) {
        return ResponseEntity.ok(assetSearchService.searchResume(request.toSearchInput()));
    }

    @GetMapping
    public ResponseEntity<AssetResponse> findById(@RequestParam("id") UUID id) {
        return ResponseEntity.ok(AssetResponse.fromEntity(assetService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AssetResponse> create(@RequestBody AssetUpsertRequest request) {
        return ResponseEntity.ok(AssetResponse.fromEntity(assetService.create(request)));
    }

    @PutMapping
    public ResponseEntity<AssetResponse> update(@RequestBody AssetUpsertRequest request, @RequestParam(name = "id") UUID id) {
        return ResponseEntity.ok(AssetResponse.fromEntity(assetService.update(request, id)));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(name = "id") UUID id) {
        assetService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
