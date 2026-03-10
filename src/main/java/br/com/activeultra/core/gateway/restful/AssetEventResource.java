package br.com.activeultra.core.gateway.restful;

import br.com.activeultra.core.gateway.dto.*;
import br.com.activeultra.core.service.AssetEventProcessService;
import br.com.activeultra.core.service.AssetEventSearchService;
import br.com.activeultra.core.service.AssetEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("asset-events")
public class AssetEventResource {

    private final AssetEventProcessService eventProcessService;
    private final AssetEventSearchService eventSearchService;
    private final AssetEventService eventService;

    public AssetEventResource(AssetEventProcessService eventProcessService,
                              AssetEventSearchService eventSearchService,
                              AssetEventService eventService) {
        this.eventProcessService = eventProcessService;
        this.eventSearchService = eventSearchService;
        this.eventService = eventService;
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<AssetEventDto>> search(@RequestBody AssetEventSearchRequest request) {
        return ResponseEntity.ok(eventSearchService.searchResume(request.toSearchInput()));
    }
    public ResponseEntity<?> changeStatus() {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AssetEventDto>> list(@RequestParam(name = "assetId") UUID assetId) {
        List<AssetEventDto> assetEventDtos = eventService.list(assetId)
                .stream().map(AssetEventDto::toDto).toList();

        return ResponseEntity.ok(assetEventDtos);
    }

    @PostMapping
    public ResponseEntity<?> process(AssetEventProcessRequest request) {
        eventProcessService.execute(request);

        return ResponseEntity.ok().build();
    }

}
