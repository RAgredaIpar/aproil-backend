package pe.noltek.aproil.backend.dto;

import java.util.List;

public record TechnologyPageDto(
        String name,
        String contentMd,
        String metaDescription,
        List<ApplicationGroup> applications
) {
    public record ApplicationGroup(
            String applicationSlug,
            String applicationName,
            List<ProductItemDto> products
    ) {}
}
