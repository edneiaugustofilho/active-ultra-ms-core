package br.com.activeultra.core.actor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtActorProvider implements ActorProvider {

    @Override
    public UUID getCurrentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Autenticação inválida.");
        }

        String id = jwt.getClaimAsString("id");
        if (id == null) {
            throw new IllegalArgumentException("Id de usuário não presente ou inválido");
        }


        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Id de usuário é inválido.");
        }
    }
}
