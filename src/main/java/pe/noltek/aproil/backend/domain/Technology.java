package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "technology",
        uniqueConstraints = @UniqueConstraint(name = "uk_technology_slug", columnNames = "slug"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Technology extends BaseAuditable {

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

    @OneToMany(mappedBy = "technology")
    @Builder.Default
    private Set<Product> products = new LinkedHashSet<>();
}
