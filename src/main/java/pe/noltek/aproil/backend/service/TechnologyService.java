package pe.noltek.aproil.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pe.noltek.aproil.backend.domain.Technology;
import pe.noltek.aproil.backend.dto.ProductItemDto;
import pe.noltek.aproil.backend.dto.TechnologyPageDto;
import pe.noltek.aproil.backend.repository.TechnologyRepository;

import java.util.*;

import static pe.noltek.aproil.backend.service.LangUtil.pick;
import static pe.noltek.aproil.backend.service.LangUtil.norm;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository techRepo;

    @Transactional(readOnly = true)
    public TechnologyPageDto getPage(String slug, String langRaw) {
        String lang = norm(langRaw);

        // 404 si no existe o inactivo
        Technology tech = techRepo.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!tech.isActive()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // Header desde la entidad (menos consultas)
        String name        = pick(lang, tech.getName(),        tech.getNameEn());
        String contentMd   = pick(lang, tech.getContentMd(),   tech.getContentMdEn());
        String metaDesc    = pick(lang, tech.getMetaDescription(), tech.getMetaDescriptionEn());

        // Apps con productos de esta tecnología
        var apps = techRepo.applicationsForTechnology(slug);

        // Productos por aplicación
        var rows = techRepo.productsByTechnologyGrouped(slug);
        Map<String, List<ProductItemDto>> byApp = new LinkedHashMap<>();
        for (var r : rows) {
            byApp.computeIfAbsent(r.getApplicationSlug(), k -> new ArrayList<>())
                    .add(new ProductItemDto(
                            r.getId(),
                            r.getSlug(),
                            pick(lang, r.getName(), r.getNameEn()),
                            pick(lang, r.getShortDescription(), r.getShortDescriptionEn()),
                            pick(lang, r.getPdfUrl(), r.getPdfUrlEn())
                    ));
        }

        // Grupos ordenados por nombre efectivo
        var groups = apps.stream()
                .sorted(Comparator.comparing(a -> pick(lang, a.getName(), a.getNameEn()), String.CASE_INSENSITIVE_ORDER))
                .map(a -> new TechnologyPageDto.ApplicationGroup(
                        a.getSlug(),
                        pick(lang, a.getName(), a.getNameEn()),
                        byApp.getOrDefault(a.getSlug(), List.of())
                )).toList();

        return new TechnologyPageDto(name, contentMd, metaDesc, groups);
    }
}
