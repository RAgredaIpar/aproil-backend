package pe.noltek.aproil.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.noltek.aproil.backend.dto.TechnologyPageDto;
import pe.noltek.aproil.backend.service.TechnologyService;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/v1/technologies", produces = "application/json")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService service;

    @GetMapping("/{slug}")
    public ResponseEntity<TechnologyPageDto> get(@PathVariable String slug,
                                                 @RequestParam(defaultValue = "es") String lang) {
        var dto = service.getPage(slug, lang);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS).cachePublic())
                .body(dto);
    }
}
