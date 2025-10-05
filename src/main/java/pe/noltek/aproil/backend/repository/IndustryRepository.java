package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.noltek.aproil.backend.domain.Industry;

import java.util.*;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    interface HeaderView {
        String getName();
        String getContentMd();
        String getMetaDescription();
    }

    interface FeaturedAppItem {
        Short getFeaturedRank();
        Long getId();
        String getSlug();
        String getName();
    }

    interface FeaturedProductItem {
        Short getFeaturedRank();
        Long getId();
        String getSlug();
        String getName();
        String getShortDescription();
        String getPdfUrl();
    }

    Optional<Industry> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("""
    select i.name as name, i.contentMd as contentMd, i.metaDescription as metaDescription
    from Industry i
    where i.active = true and i.slug = :slug
  """)
    Optional<HeaderView> headerBySlug(@Param("slug") String slug);

    @Query("""
    select ia.featuredRank as featuredRank,
           a.id as id, a.slug as slug, a.name as name
    from IndustryApplication ia
      join ia.industry i
      join ia.application a
    where i.slug = :industrySlug
      and i.active = true
      and a.active = true
      and ia.featuredRank is not null
    order by ia.featuredRank asc
  """)
    List<FeaturedAppItem> featuredApplications(@Param("industrySlug") String industrySlug);

    @Query("""
    select ipf.featuredRank as featuredRank,
           p.id as id, p.slug as slug, p.name as name, p.shortDescription as shortDescription, p.pdfUrl as pdfUrl
    from IndustryProductFeature ipf
      join ipf.industry i
      join ipf.product p
    where i.slug = :industrySlug
      and i.active = true
      and p.active = true
    order by ipf.featuredRank asc
  """)
    List<FeaturedProductItem> featuredProducts(@Param("industrySlug") String industrySlug);
}
