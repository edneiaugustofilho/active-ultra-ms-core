package br.com.activeultra.core.gateway.dto;

import br.com.activeultra.core.enums.AssetCategory;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetByCategoryDto {

    private AssetCategory category;
    private Long total;
    private BigDecimal totalAcquisitionValue;
}