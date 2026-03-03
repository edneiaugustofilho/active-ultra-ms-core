package br.com.activeultra.core.config;

import br.com.activeultra.core.exceptions.CustomTokenExpiredException;
import br.com.activeultra.core.gateway.dto.ApiError;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), "BAD_REQUEST"));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> handleException(DisabledException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Credenciais inválidas", "UNAUTHORIZED"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Credenciais inválidas", "UNAUTHORIZED"));
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ApiError> handleException(ChangeSetPersister.NotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(e.getMessage(), "NOT_FOUND"));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ApiError> handleException(JWTVerificationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiError("Token inválido", "FORBIDDEN"));
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ApiError> handleException(JWTCreationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError("Erro ao criar Token", "INTERNAL_SERVER_ERROR"));
    }

    @ExceptionHandler(CustomTokenExpiredException.class)
    public ResponseEntity<ApiError> handleException(CustomTokenExpiredException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiError("O Token expirou", "FORBIDDEN"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(e.getMessage(), "BAD_REQUEST"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new ApiError("Erro interno de execução", "INTERNAL_SERVER_ERROR"));
    }
}
