package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
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

    @Column(length = 64, nullable = false)
    private String slug;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String shortDescription;

    /** PDF en storage externo (URL/ruta) */
    @Column(nullable = false, columnDefinition = "text")
    private String pdfUrl;

    /** EN **/
    @Column(name = "name_en", length = 120)
    private String nameEn;

    @Column(name = "short_description_en", columnDefinition = "text")
    private String shortDescriptionEn;

    /** PDF en storage externo (URL/ruta) */
    @Column(name = "pdf_url_en", columnDefinition = "text")
    private String pdfUrlEn;

    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private Set<IndustryProductFeature> industryFeatures = new LinkedHashSet<>();
}

