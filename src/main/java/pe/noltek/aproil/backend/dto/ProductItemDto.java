package pe.noltek.aproil.backend.dto;

public record ProductItemDto(
        Long id,
        String slug,
        String name,
        String shortDescription,
        String pdfUrl
) {
}
