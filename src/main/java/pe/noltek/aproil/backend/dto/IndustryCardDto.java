package pe.noltek.aproil.backend.dto;

public record IndustryCardDto(
        String slug,
        String name,
        String imageUrl,
        String imageAlt
) {
}