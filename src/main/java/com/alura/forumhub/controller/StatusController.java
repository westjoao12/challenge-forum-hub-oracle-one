package com.alura.forumhub.controller;

import com.alura.forumhub.infra.status.StatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@RestController
@Tag(name = "1. API Status")
public class StatusController {
    @GetMapping("/")
    @Operation(hidden = true)
    public RedirectView home() { return new RedirectView("/api/status"); }

    @GetMapping("/docs")
    @Operation(summary = "Redireciona para a documentação Swagger UI")
    public RedirectView docs() {
        return new RedirectView("/api/swagger-ui.html");
    }

    @GetMapping("/status")
    @Operation(summary = "Verifica o status e integridade da aplicação")
    public StatusResponse status() {
        return new StatusResponse(
                "ForumHub API is running!",
                "1.0.0",
                "OK",
                "/api/docs",
                LocalDateTime.now().toString()
        );
    }
}
