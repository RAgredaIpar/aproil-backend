package pe.noltek.aproil.backend.dto;

import java.util.List;

public record IndustryPageDto(
        String name,
        String contentMd,
        String metaDescription,
        List<FeaturedAppDto> featuredApplications,
        List<FeaturedProductDto> featuredProducts
) {
}
