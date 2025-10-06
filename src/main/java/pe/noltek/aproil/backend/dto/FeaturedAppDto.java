package pe.noltek.aproil.backend.dto;

public record FeaturedAppDto(
        short featuredRank,
        Long id,
        String slug,
        String name
) {
}
