package com.acme.catchup.platform.news.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Resource for creating a favorite news source.
 * @summary
 * Create Favorite News Source Resource.
 * Represents the data required to favorite a news source.
 * @param newsApiKey The API key for accessing the news service. Must not be blank.
 * @param sourceId The identifier of the news source to be favorite. Must not be blank.
 *
 * @since 1.0.0
 *
 */
public record CreateFavoriteSourceResource(
        @NotBlank(message = "{favorite.source.error.newsApiKey.notBlank}")
        String newsApiKey,
        @NotBlank(message = "{favorite.source.error.sourceId.notBlank}")
        String sourceId) { }
