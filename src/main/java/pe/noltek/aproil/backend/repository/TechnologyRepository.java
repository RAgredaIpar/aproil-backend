package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.noltek.aproil.backend.domain.Technology;

import java.util.*;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    interface HeaderView {
        String getName();
        String getContentMd();
        String getMetaDescription();
    }

    interface ApplicationListItem {
        Long getId();
        String getSlug();
        String getName();
    }

    interface ProductByAppRow {
        String getApplicationSlug();
        Long getId();
        String getSlug();
        String getName();
        String getShortDescription();
        String getPdfUrl();
    }

    Optional<Technology> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("""
    select t.name as name, t.contentMd as contentMd, t.metaDescription as metaDescription
    from Technology t
    where t.active = true and t.slug = :slug
  """)
    Optional<HeaderView> headerBySlug(@Param("slug") String slug);

    @Query("""
    select distinct a.id as id, a.slug as slug, a.name as name
    from Application a
      join a.products p
    where p.technology.slug = :techSlug
      and a.active = true
      and p.active = true
    order by a.name asc
  """)
    List<ApplicationListItem> applicationsForTechnology(@Param("techSlug") String techSlug);

    @Query("""
    select a.slug as applicationSlug,
           p.id as id, p.slug as slug, p.name as name, p.shortDescription as shortDescription, p.pdfUrl as pdfUrl
    from Application a
      join a.products p
    where p.technology.slug = :techSlug
      and a.active = true
      and p.active = true
    order by a.name asc, p.name asc
  """)
    List<ProductByAppRow> productsByTechnologyGrouped(@Param("techSlug") String techSlug);
}
