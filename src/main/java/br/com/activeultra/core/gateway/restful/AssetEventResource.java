package br.com.activeultra.core.gateway.restful;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetEventResource {


    public ResponseEntity<?> changeStatus() {
        return ResponseEntity.ok().build();
    }

}
