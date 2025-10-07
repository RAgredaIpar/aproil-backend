package pe.noltek.aproil.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pe.noltek.aproil.backend.domain.Application;
import pe.noltek.aproil.backend.dto.ApplicationCardDto;
import pe.noltek.aproil.backend.dto.ApplicationPageDto;
import pe.noltek.aproil.backend.dto.ProductItemDto;
import pe.noltek.aproil.backend.repository.ApplicationRepository;

import java.util.*;

import static pe.noltek.aproil.backend.service.LangUtil.pick;
import static pe.noltek.aproil.backend.service.LangUtil.norm;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository appRepo;

    @Transactional(readOnly = true)
    public ApplicationPageDto getPage(String slug, String langRaw) {
        String lang = norm(langRaw);

        Application app = appRepo.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!app.isActive()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String name = pick(lang, app.getName(), app.getNameEn());
        String contentMd = pick(lang, app.getContentMd(), app.getContentMdEn());
        String metaDesc = pick(lang, app.getMetaDescription(), app.getMetaDescriptionEn());

        var techs = appRepo.technologiesForApplication(slug);

        var rows = appRepo.productsByApplicationGrouped(slug);
        Map<String, List<ProductItemDto>> byTech = new LinkedHashMap<>();
        for (var r : rows) {
            byTech.computeIfAbsent(r.getTechnologySlug(), k -> new ArrayList<>())
                    .add(new ProductItemDto(
                            r.getId(),
                            r.getSlug(),
                            pick(lang, r.getName(), r.getNameEn()),
                            pick(lang, r.getShortDescription(), r.getShortDescriptionEn()),
                            pick(lang, r.getPdfUrl(), r.getPdfUrlEn())
                    ));
        }

        var groups = techs.stream()
                .sorted(Comparator.comparing(t -> pick(lang, t.getName(), t.getNameEn()), String.CASE_INSENSITIVE_ORDER))
                .map(t -> new ApplicationPageDto.TechnologyGroup(
                        t.getSlug(),
                        pick(lang, t.getName(), t.getNameEn()),
                        byTech.getOrDefault(t.getSlug(), List.of())
                )).toList();

        return new ApplicationPageDto(name, contentMd, metaDesc, groups);
    }

    // Grid “todas las aplicaciones”
    @Transactional(readOnly = true)
    public List<ApplicationCardDto> listCards(String langRaw) {
        String lang = norm(langRaw);
        return appRepo.listCards().stream()
                .map(r -> new ApplicationCardDto(
                        r.getSlug(),
                        pick(lang, r.getName(), r.getNameEn()),
                        r.getImageUrl(),
                        pick(lang, r.getImageAlt(), r.getImageAltEn())
                ))
                .sorted(Comparator.comparing(ApplicationCardDto::name, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
