package pe.noltek.aproil.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.noltek.aproil.backend.dto.IndustryCardDto;
import pe.noltek.aproil.backend.dto.IndustryPageDto;
import pe.noltek.aproil.backend.service.IndustryService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/v1/industries", produces = "application/json")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService service;

    // Página de una industria
    @GetMapping("/{slug}")
    public ResponseEntity<IndustryPageDto> get(@PathVariable String slug,
                                               @RequestParam(defaultValue = "es") String lang) {
        var dto = service.getPage(slug, lang);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS).cachePublic())
                .body(dto);
    }

    // Grid "todas las industrias"
    @GetMapping
    public ResponseEntity<List<IndustryCardDto>> list(@RequestParam(defaultValue = "es") String lang) {
        var list = service.listCards(lang);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS).cachePublic())
                .body(list);
    }
}
