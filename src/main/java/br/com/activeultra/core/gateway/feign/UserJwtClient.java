package br.com.activeultra.core.gateway.feign;

import br.com.activeultra.core.config.feign.UserJwtFeignConfig;
import br.com.activeultra.core.gateway.dto.AuthUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "userJwtClient",
        url = "${app.user-jwt.base-url}",
        configuration = UserJwtFeignConfig.class
)
public interface UserJwtClient {

    @GetMapping("/user")
    AuthUserDto getUserById(@RequestParam("id") UUID id);
}
