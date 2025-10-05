package pe.noltek.aproil.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.noltek.aproil.backend.domain.IndustryApplication;

public interface IndustryApplicationRepository extends JpaRepository<IndustryApplication, Long> { }
