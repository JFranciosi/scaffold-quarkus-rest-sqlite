package repository;

import entity.Market;
import entity.Theme;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.PageResponse;

@ApplicationScoped
public class MarketRepository implements PanacheRepositoryBase<Market, Long> {

    public PageResponse<Market> findFiltered(Theme theme, String search, int page, int size) {
        StringBuilder query = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (theme != null) {
            query.append(" AND theme = :theme");
            params.put("theme", theme);
        }

        if (search != null && !search.isBlank()) {
            query.append(" AND LOWER(title) LIKE LOWER(:search)");
            params.put("search", "%" + search + "%");
        }

        int safeSize = Math.max(size, 1);
        int safePage = Math.max(page, 0);

        var panacheQuery = find(query.toString(), params);
        long totalElements = panacheQuery.count();
        List<Market> content = panacheQuery.page(Page.of(safePage, safeSize)).list();
        int totalPages = (int) Math.ceil((double) totalElements / safeSize);

        return new PageResponse<>(content, totalElements, totalPages, safePage, safeSize);
    }
}
