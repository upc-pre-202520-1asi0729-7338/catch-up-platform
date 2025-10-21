package com.acme.catchup.platform.news.interfaces.rest;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.queries.GetFavoriteSourceByIdQuery;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceCommandService;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceQueryService;
import com.acme.catchup.platform.news.interfaces.rest.resources.CreateFavoriteSourceResource;
import com.acme.catchup.platform.news.interfaces.rest.resources.FavoriteSourceResource;
import com.acme.catchup.platform.news.interfaces.rest.transform.CreateFavoriteSourceCommandFromResourceAssembler;
import com.acme.catchup.platform.news.interfaces.rest.transform.FavoriteSourceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/favorite-sources", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Favorite Sources", description = "Endpoints for managing user's favorite news sources")
public class FavoriteSourcesController {
    private final FavoriteSourceCommandService favoriteSourceCommandService;
    private final FavoriteSourceQueryService favoriteSourceQueryService;

    public FavoriteSourcesController(FavoriteSourceCommandService favoriteSourceCommandService, FavoriteSourceQueryService favoriteSourceQueryService) {
        this.favoriteSourceCommandService = favoriteSourceCommandService;
        this.favoriteSourceQueryService = favoriteSourceQueryService;
    }


    @Operation(
            summary = "Get Favorite Source by ID",
            description = "Retrieve a favorite news source by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite source found and returned"),
            @ApiResponse(responseCode = "404", description = "Favorite source not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<FavoriteSourceResource> getFavoriteSourceById(@PathVariable Long id) {
        Optional<FavoriteSource> favoriteSource = favoriteSourceQueryService.handle(new GetFavoriteSourceByIdQuery(id));
        return favoriteSource
                .map(source -> ResponseEntity.ok(FavoriteSourceResourceFromEntityAssembler.toResourceFromEntity(source)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Favorite Source",
            description = "Add a new favorite news source for the user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorite source created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<FavoriteSourceResource> createFavoriteSource(@RequestBody CreateFavoriteSourceResource resource) {
        Optional<FavoriteSource> favoriteSource = favoriteSourceCommandService.handle(CreateFavoriteSourceCommandFromResourceAssembler.toCommandFromResource(resource));
        return favoriteSource
                .map(source -> new ResponseEntity<>(FavoriteSourceResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
