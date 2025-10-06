package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.noltek.aproil.backend.domain.Application;
import java.util.*;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    interface HeaderView {
        String getName();            String getNameEn();
        String getContentMd();       String getContentMdEn();
        String getMetaDescription(); String getMetaDescriptionEn();
    }

    interface CardItem {
        String getSlug();
        String getName(); String getNameEn();
        String getImageUrl();
        String getImageAlt(); String getImageAltEn();
    }

    @Query("""
    select a.slug as slug,
           a.name as name, a.nameEn as nameEn,
           a.imageUrl as imageUrl,
           a.imageAlt as imageAlt, a.imageAltEn as imageAltEn
    from Application a
    where a.active = true
    order by a.name asc
  """)
    List<CardItem> listCards();

    interface TechnologyListItem {
        Long getId();
        String getSlug();
        String getName();  String getNameEn();
    }

    interface ProductByTechRow {
        String getTechnologySlug();
        String getTechnologyName();   String getTechnologyNameEn();

        Long getId();
        String getSlug();
        String getName();             String getNameEn();
        String getShortDescription(); String getShortDescriptionEn();
        String getPdfUrl();           String getPdfUrlEn();
    }

    Optional<Application> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("""
              select a.name as name, a.nameEn as nameEn,
                     a.contentMd as contentMd, a.contentMdEn as contentMdEn,
                     a.metaDescription as metaDescription, a.metaDescriptionEn as metaDescriptionEn
              from Application a
              where a.active = true and a.slug = :slug
            """)
    Optional<HeaderView> headerBySlug(@Param("slug") String slug);

    @Query("""
    select distinct t.id as id, t.slug as slug, t.name as name, t.nameEn as nameEn
    from Technology t
      join t.products p
      join p.applications a
    where a.slug = :appSlug
      and t.active = true
      and p.active = true
      and a.active = true
    order by t.name asc
  """)
    List<TechnologyListItem> technologiesForApplication(@Param("appSlug") String appSlug);

    @Query("""
    select t.slug as technologySlug,
           t.name as technologyName, t.nameEn as technologyNameEn,
           p.id as id, p.slug as slug,
           p.name as name, p.nameEn as nameEn,
           p.shortDescription as shortDescription, p.shortDescriptionEn as shortDescriptionEn,
           p.pdfUrl as pdfUrl, p.pdfUrlEn as pdfUrlEn
    from Technology t
      join t.products p
      join p.applications a
    where a.slug = :appSlug
      and t.active = true
      and p.active = true
      and a.active = true
    order by t.name asc, p.name asc
  """)
    List<ProductByTechRow> productsByApplicationGrouped(@Param("appSlug") String appSlug);
}
