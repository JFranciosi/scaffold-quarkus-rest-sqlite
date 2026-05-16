package repository;

import entity.Market;
import entity.Theme;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MarketRepository implements PanacheRepositoryBase<Market, Long> {

    public List<Market> findFiltered(Theme theme, String search) {
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

        return list(query.toString(), params);
    }
}
