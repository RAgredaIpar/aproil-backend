package pe.noltek.aproil.backend.dto;

public record FeaturedProductDto(
        short featuredRank,
        Long id,
        String slug,
        String name,
        String shortDescription,
        String pdfUrl
) {
}
