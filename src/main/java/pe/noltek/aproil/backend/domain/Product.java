package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "product",
        uniqueConstraints = @UniqueConstraint(name = "uk_product_slug", columnNames = "slug"),
        indexes = @Index(name = "idx_product_technology", columnList = "technology_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Product extends BaseAuditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Un producto pertenece a UNA tecnología */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technology_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_technology"))
    private Technology technology;

    @NotBlank
    @Size(max = 64)
    @Pattern(regexp = "^[a-z0-9-]+$", message = "debe ser minúsculas, números o guiones")
    @Column(length = 64, nullable = false)
    private String slug;

    @NotBlank @Size(max = 120)
    @Column(length = 120, nullable = false)
    private String name;

    @Size(max = 500)
    @Column(columnDefinition = "text")
    private String shortDescription;

    /** PDF en storage externo (URL/ruta) */
    @Size(max = 512)
    @Column(nullable = false, columnDefinition = "text")
    private String pdfUrl;

    /** EN **/
    @Size(max = 120)
    @Column(name = "name_en", length = 120)
    private String nameEn;

    @Size(max = 500)
    @Column(name = "short_description_en", columnDefinition = "text")
    private String shortDescriptionEn;

    /** PDF en storage externo (URL/ruta) */
    @Size(max = 512)
    @Column(name = "pdf_url_en", columnDefinition = "text")
    private String pdfUrlEn;

    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private Set<IndustryProductFeature> industryFeatures = new LinkedHashSet<>();
}

