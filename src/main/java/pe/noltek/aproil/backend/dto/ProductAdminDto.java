package pe.noltek.aproil.backend.dto;

public record ProductAdminDto(
        Long id,
        String slug,
        boolean active,
        Long technologyId,
        // ES
        String name,
        String shortDescription,
        String pdfUrl,
        // EN
        String nameEn,
        String shortDescriptionEn,
        String pdfUrlEn
) {
}