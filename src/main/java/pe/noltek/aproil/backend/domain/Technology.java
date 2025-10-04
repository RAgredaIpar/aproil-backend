package pe.noltek.aproil.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "technology",
        uniqueConstraints = @UniqueConstraint(name = "uk_technology_slug", columnNames = "slug"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
public class Technology {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    private String slug;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "technology")
    @Builder.Default
    private Set<Product> products = new LinkedHashSet<>();
}
