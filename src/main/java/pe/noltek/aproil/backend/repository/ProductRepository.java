package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.noltek.aproil.backend.domain.Product;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
