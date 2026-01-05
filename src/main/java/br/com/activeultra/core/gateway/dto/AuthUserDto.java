package br.com.activeultra.core.gateway.dto;

import java.util.List;
import java.util.UUID;

public record AuthUserDto(UUID id, String email, List<String> roles) {
}
