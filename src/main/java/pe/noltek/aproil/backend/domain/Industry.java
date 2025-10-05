package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "industry",
        uniqueConstraints = @UniqueConstraint(name = "uk_industry_slug", columnNames = "slug"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Industry extends BaseAuditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    private String slug;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(name = "content_md", columnDefinition = "text")
    private String contentMd;

    @Column(name = "meta_description", length = 160)
    private String metaDescription;

    /** EN **/
    @Column(name = "name_en", length = 120)
    private String nameEn;

    @Column(name = "content_md_en", columnDefinition = "text")
    private String contentMdEn;

    @Column(name = "meta_description_en", length = 160)
    private String metaDescriptionEn;

    @OneToMany(mappedBy = "industry")
    @Builder.Default
    private Set<IndustryApplication> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "industry")
    @Builder.Default
    private Set<IndustryProductFeature> featuredProducts = new LinkedHashSet<>();
}
