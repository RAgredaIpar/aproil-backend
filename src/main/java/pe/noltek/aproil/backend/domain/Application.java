package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "application",
        uniqueConstraints = @UniqueConstraint(name = "uk_application_slug", columnNames = "slug"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Application extends BaseAuditable {

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

    /** M:N con Product SIN entidad intermedia (Hibernate crea application_product) */
    @ManyToMany
    @JoinTable(
            name = "application_product",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private Set<Product> products = new LinkedHashSet<>();

    /** Backrefs a relaciones con datos (no necesarias para consultas básicas) */
    @OneToMany(mappedBy = "application")
    @Builder.Default
    private Set<IndustryApplication> industryLinks = new LinkedHashSet<>();
}
