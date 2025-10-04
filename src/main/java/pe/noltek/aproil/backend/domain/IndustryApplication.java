package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "industry_application",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_indapp_pair",
                        columnNames = {"industry_id","application_id"}),
                // Un solo slot por industria; Postgres permite múltiples NULL
                @UniqueConstraint(name = "uk_indapp_feature_slot",
                        columnNames = {"industry_id","featured_rank"})
        })
@Check(constraints = "featured_rank IS NULL OR (featured_rank BETWEEN 1 AND 4)")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
public class IndustryApplication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "industry_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_indapp_industry"))
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_indapp_application"))
    private Application application;

    /** 1..4 (NULL = no destacado) */
    @Column(name = "featured_rank")
    private Short featuredRank;
}
