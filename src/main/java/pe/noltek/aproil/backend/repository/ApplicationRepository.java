package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.noltek.aproil.backend.domain.Application;

import java.util.*;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    interface HeaderView {
        String getName();
        String getContentMd();
        String getMetaDescription();
    }

    interface TechnologyListItem {
        Long getId();
        String getSlug();
        String getName();
    }

    interface ProductByTechRow {
        String getTechnologySlug();
        Long getId();
        String getSlug();
        String getName();
        String getShortDescription();
        String getPdfUrl();
    }

    Optional<Application> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("""
    select a.name as name, a.contentMd as contentMd, a.metaDescription as metaDescription
    from Application a
    where a.active = true and a.slug = :slug
  """)
    Optional<HeaderView> headerBySlug(@Param("slug") String slug);

    @Query("""
    select distinct t.id as id, t.slug as slug, t.name as name
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
           p.id as id, p.slug as slug, p.name as name, p.shortDescription as shortDescription, p.pdfUrl as pdfUrl
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
