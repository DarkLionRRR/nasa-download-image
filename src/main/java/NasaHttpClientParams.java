import java.util.Objects;

public class NasaHttpClientParams {
    private final String apiKey;

    public String date;
    public String startDate;
    public String endDate;
    public Integer count;
    public Boolean thumbs;

    public NasaHttpClientParams(
            String apiKey,
            String date,
            String startDate,
            String endDate,
            Integer count,
            Boolean thumbs
    ) {
        this.apiKey = apiKey;
        this.date = date == null ? "" : date;
        this.startDate = startDate == null ? "" : startDate;
        this.endDate = endDate == null ? "" : endDate;
        this.count = count == null ? 0 : count;
        this.thumbs = thumbs;
    }

    public String getSerializeParams() {
        String params = "?api_key=" + this.apiKey;

        if (!Objects.equals(this.date, "")) {
            params += "&date=" + this.date;
        }

        if (!Objects.equals(this.startDate, "") && Objects.equals(this.date, "")) {
            params += "&start_date=" + this.startDate;
        }

        if (!Objects.equals(this.endDate, "") && !Objects.equals(this.startDate, "")) {
            params += "&end_date=" + this.endDate;
        }

        if (this.count > 0 && Objects.equals(this.date, "") && Objects.equals(this.startDate, "")) {
            params += "&count=" + this.count;
        }

        if (this.thumbs) {
            params += "&thumbs=" + true;
        }

        return params;
    }
}
