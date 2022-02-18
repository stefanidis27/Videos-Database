package fileio;

import java.util.ArrayList;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * Total number of views
     */
    private int totalViews;
    /**
     * Total rating for each show
     */
    private double totalRating;
    /**
     * Total times the movie was added as a favorite
     */
    private int totalFavorite;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.totalViews = 0;
        this.totalRating = 0.0;
        this.totalFavorite = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int getTotalViews() {
        return totalViews;
    }

    public final void setTotalViews(final int totalViews) {
        this.totalViews = totalViews;
    }

    public final double getTotalRating() {
        return totalRating;
    }

    public final void setTotalRating(final double totalRating) {
        this.totalRating = totalRating;
    }

    public final int getTotalFavorite() {
        return totalFavorite;
    }

    public final void setTotalFavorite(final int totalFavorite) {
        this.totalFavorite = totalFavorite;
    }
}
