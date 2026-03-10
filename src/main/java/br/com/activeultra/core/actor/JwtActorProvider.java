package br.com.activeultra.core.actor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtActorProvider implements ActorProvider {

    private Jwt getJwt() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Usuário não autenticado.");
        }

        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Autenticação inválida.");
        }

        return jwt;
    }

    @Override
    public UUID getCurrentUserId() {
        String id = getJwt().getClaimAsString("id");
        if (id == null) {
            throw new IllegalStateException("Id de usuário não presente ou inválido");
        }


        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new IllegalStateException("Id de usuário é inválido.");
        }
    }

    @Override
    public String getCurrenteUserName() {
        String username = getJwt().getSubject();
        if (username == null) {
            throw new IllegalStateException("Nome de usuário não presente ou inválido");
        }


        return username;
    }
}
