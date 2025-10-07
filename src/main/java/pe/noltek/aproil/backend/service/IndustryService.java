package pe.noltek.aproil.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pe.noltek.aproil.backend.domain.Industry;
import pe.noltek.aproil.backend.dto.*;
import pe.noltek.aproil.backend.repository.IndustryRepository;

import java.util.Comparator;
import java.util.List;

import static pe.noltek.aproil.backend.service.LangUtil.pick;
import static pe.noltek.aproil.backend.service.LangUtil.norm;

@Service
@RequiredArgsConstructor
public class IndustryService {

    private final IndustryRepository indRepo;

    @Transactional(readOnly = true)
    public IndustryPageDto getPage(String slug, String langRaw) {
        String lang = norm(langRaw);

        Industry ind = indRepo.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!ind.isActive()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String name        = pick(lang, ind.getName(),        ind.getNameEn());
        String contentMd   = pick(lang, ind.getContentMd(),   ind.getContentMdEn());
        String metaDesc    = pick(lang, ind.getMetaDescription(), ind.getMetaDescriptionEn());

        var apps = indRepo.featuredApplications(slug).stream()
                .sorted(Comparator.comparing(IndustryRepository.FeaturedAppItem::getFeaturedRank)
                        .thenComparing(a -> pick(lang, a.getName(), a.getNameEn()), String.CASE_INSENSITIVE_ORDER))
                .map(a -> new FeaturedAppDto(
                        a.getFeaturedRank(),
                        a.getId(),
                        a.getSlug(),
                        pick(lang, a.getName(), a.getNameEn())
                )).toList();

        var prods = indRepo.featuredProducts(slug).stream()
                .sorted(Comparator.comparing(IndustryRepository.FeaturedProductItem::getFeaturedRank)
                        .thenComparing(p -> pick(lang, p.getName(), p.getNameEn()), String.CASE_INSENSITIVE_ORDER))
                .map(p -> new FeaturedProductDto(
                        p.getFeaturedRank(),
                        p.getId(),
                        p.getSlug(),
                        pick(lang, p.getName(), p.getNameEn()),
                        pick(lang, p.getShortDescription(), p.getShortDescriptionEn()),
                        pick(lang, p.getPdfUrl(), p.getPdfUrlEn())
                )).toList();

        return new IndustryPageDto(name, contentMd, metaDesc, apps, prods);
    }

    // Grid “todas las industrias”
    @Transactional(readOnly = true)
    public List<IndustryCardDto> listCards(String langRaw) {
        String lang = norm(langRaw);
        return indRepo.listCards().stream()
                .map(r -> new IndustryCardDto(
                        r.getSlug(),
                        pick(lang, r.getName(), r.getNameEn()),
                        r.getImageUrl(),
                        pick(lang, r.getImageAlt(), r.getImageAltEn())
                ))
                .sorted(Comparator.comparing(IndustryCardDto::name, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
