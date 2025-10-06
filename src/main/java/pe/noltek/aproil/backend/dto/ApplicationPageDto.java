package pe.noltek.aproil.backend.dto;

import java.util.List;

public record ApplicationPageDto(
        String name,
        String contentMd,
        String metaDescription,
        List<TechnologyGroup> technologies
) {
    public record TechnologyGroup(
            String technologySlug,
            String technologyName,
            List<ProductItemDto> products
    ) {}
}
