package pl.edu.pb.wi.model.db.response;

//{"_id": "ev0", "avgRating": 3.5}
public class AvgRatingResult {
    private Double avgRating;

    public AvgRatingResult() {
    }

    public AvgRatingResult(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}
