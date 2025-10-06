package pe.noltek.aproil.backend.dto;

public record ApplicationCardDto(
        String slug,
        String name,
        String imageUrl,
        String imageAlt
) {}
