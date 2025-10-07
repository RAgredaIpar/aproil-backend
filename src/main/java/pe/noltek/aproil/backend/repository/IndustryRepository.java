package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.noltek.aproil.backend.domain.Industry;
import java.util.*;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    interface HeaderView {
        String getName();            String getNameEn();
        String getContentMd();       String getContentMdEn();
        String getMetaDescription(); String getMetaDescriptionEn();
    }

    interface CardItem {
        String getSlug();
        String getName();           String getNameEn();
        String getImageUrl();
        String getImageAlt();       String getImageAltEn();
    }

    @Query("""
            select i.slug as slug,
                     i.name as name, i.nameEn as nameEn,
                     i.imageUrl as imageUrl,
                     i.imageAlt as imageAlt, i.imageAltEn as imageAltEn
              from Industry i
              where i.active = true
              order by i.name asc
            """)
    List<CardItem> listCards();

    interface FeaturedAppItem {
        Short getFeaturedRank();
        Long getId();
        String getSlug();
        String getName();  String getNameEn();
    }

    interface FeaturedProductItem {
        Short getFeaturedRank();
        Long getId();
        String getSlug();
        String getName();             String getNameEn();
        String getShortDescription(); String getShortDescriptionEn();
        String getPdfUrl();           String getPdfUrlEn();
    }

    Optional<Industry> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("""
              select i.name as name, i.nameEn as nameEn,
                     i.contentMd as contentMd, i.contentMdEn as contentMdEn,
                     i.metaDescription as metaDescription, i.metaDescriptionEn as metaDescriptionEn
              from Industry i
              where i.active = true and i.slug = :slug
            """)
    Optional<HeaderView> headerBySlug(@Param("slug") String slug);

    @Query("""
              select ia.featuredRank as featuredRank,
                     a.id as id, a.slug as slug, a.name as name, a.nameEn as nameEn
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
                     p.id as id, p.slug as slug,
                     p.name as name, p.nameEn as nameEn,
                     p.shortDescription as shortDescription, p.shortDescriptionEn as shortDescriptionEn,
                     p.pdfUrl as pdfUrl, p.pdfUrlEn as pdfUrlEn
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
