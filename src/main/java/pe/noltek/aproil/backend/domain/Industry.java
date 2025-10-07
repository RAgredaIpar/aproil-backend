package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "industry",
        uniqueConstraints = @UniqueConstraint(name = "uk_industry_slug", columnNames = "slug"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Industry extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = "^[a-z0-9-]+$", message = "debe ser minúsculas, números o guiones")
    @Column(length = 64, nullable = false)
    private String slug;

    @NotBlank
    @Size(max = 120)
    @Column(length = 120, nullable = false)
    private String name;

    @Size(max = 10000)
    @Column(name = "content_md", columnDefinition = "text")
    private String contentMd;

    @Size(max = 160)
    @Column(name = "meta_description", length = 160)
    private String metaDescription;

    @Size(max = 512)
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Size(max = 180)
    @Column(name = "image_alt", length = 180)
    private String imageAlt;

    @Size(max = 180)
    @Column(name = "image_alt_en", length = 180)
    private String imageAltEn;

    /**
     * EN
     **/
    @Size(max = 120)
    @Column(name = "name_en", length = 120)
    private String nameEn;

    @Size(max = 10000)
    @Column(name = "content_md_en", columnDefinition = "text")
    private String contentMdEn;

    @Size(max = 160)
    @Column(name = "meta_description_en", length = 160)
    private String metaDescriptionEn;

    @OneToMany(mappedBy = "industry")
    @Builder.Default
    private Set<IndustryApplication> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "industry")
    @Builder.Default
    private Set<IndustryProductFeature> featuredProducts = new LinkedHashSet<>();
}
