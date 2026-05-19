package resource;


import entity.Market;
import entity.Theme;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.PageResponse;
import repository.MarketRepository;

@Path("/markets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MarketResource {
    private final MarketRepository marketRepository;

    public MarketResource(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    @GET
    public PageResponse<Market> findAll
            (
            @QueryParam("theme") Theme theme,
            @QueryParam("search") String search,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("6") @QueryParam("size") int size) {
        return marketRepository.findFiltered(theme, search, page, size);
    }

    @GET
    @Path("/{id}")
    public Market get(@PathParam("id") Long id) {
        Market market = marketRepository.findById(id);
        if (market == null) throw new NotFoundException();
        return market;
    }

    @POST
    @Transactional
    public Response create(Market market) {
        marketRepository.persist(market);
        return Response.status(Response.Status.CREATED).entity(market).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Market update(@PathParam("id") Long id, Market updated) {
        Market market = marketRepository.findById(id);
        if (market == null) throw new NotFoundException();
        market.setTitle(updated.getTitle());
        market.setDescription(updated.getDescription());
        market.setSquare(updated.getSquare());
        market.setTheme(updated.getTheme());
        market.setTotalDesks(updated.getTotalDesks());
        return market;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = marketRepository.deleteById(id);
        if (!deleted) throw new NotFoundException();
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/BookDesks")
    @Transactional
    public Response bookDesk(@PathParam("id") Long id) {
        Market market = marketRepository.findById(id);
        if (market == null) throw new NotFoundException();

        if (market.getAssignedDesks() >= market.getTotalDesks()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Nessun banco disponibile")
                    .build();
        }

        market.setAssignedDesks(market.getAssignedDesks() + 1);
        return Response.ok(market).build();
    }
}