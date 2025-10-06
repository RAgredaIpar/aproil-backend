package pe.noltek.aproil.backend.dto;

public record IndustryAdminDto(
        Long id,
        String slug,
        boolean active,
        // ES
        String name,
        String contentMd,
        String metaDescription,
        // EN
        String nameEn,
        String contentMdEn,
        String metaDescriptionEn
) {
}
