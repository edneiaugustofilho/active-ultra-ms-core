package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.enums.AssetStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetByStatusDto {

    private AssetStatus status;
    private Long total;
    private BigDecimal totalAcquisitionValue;
}
