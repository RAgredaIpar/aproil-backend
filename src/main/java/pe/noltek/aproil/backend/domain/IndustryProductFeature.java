package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "industry_product_feature",
        uniqueConstraints = {
                // evita repetir el mismo producto entre los 3 de una industria
                @UniqueConstraint(name = "uk_indprod_once",
                        columnNames = {"industry_id","product_id"}),
                // slots 1..3 únicos por industria
                @UniqueConstraint(name = "uk_indprod_feature_slot",
                        columnNames = {"industry_id","featured_rank"})
        })
@Check(constraints = "featured_rank BETWEEN 1 AND 3")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
public class IndustryProductFeature {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "industry_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_indprod_industry"))
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_indprod_product"))
    private Product product;

    /** 1..3 (orden editorial) */
    @Column(name = "featured_rank", nullable = false)
    private Short featuredRank;
}
