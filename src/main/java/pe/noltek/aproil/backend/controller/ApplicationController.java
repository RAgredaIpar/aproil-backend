package pe.noltek.aproil.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.noltek.aproil.backend.dto.ApplicationCardDto;
import pe.noltek.aproil.backend.dto.ApplicationPageDto;
import pe.noltek.aproil.backend.service.ApplicationService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/v1/applications", produces = "application/json")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    // Página de una aplicación
    @GetMapping("/{slug}")
    public ResponseEntity<ApplicationPageDto> get(@PathVariable String slug,
                                                  @RequestParam(defaultValue = "es") String lang) {
        var dto = service.getPage(slug, lang);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS).cachePublic())
                .body(dto);
    }

    // Grid "todas las aplicaciones"
    @GetMapping
    public ResponseEntity<List<ApplicationCardDto>> list(@RequestParam(defaultValue = "es") String lang) {
        var list = service.listCards(lang);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS).cachePublic())
                .body(list);
    }
}
