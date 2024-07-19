import com.fasterxml.jackson.annotation.JsonProperty;

public class NasaAnswer {
    String copyright;
    String date;
    String explanation;
    String hdUrl;
    String mediaType;
    String serviceVersion;
    String title;
    String url;

    public NasaAnswer(
            @JsonProperty("copyright") String copyright,
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdUrl,
            @JsonProperty("media_type") String mediaType,
            @JsonProperty("service_version") String serviceVersion,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url
    ) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdUrl = hdUrl;
        this.mediaType = mediaType;
        this.serviceVersion = serviceVersion;
        this.title = title;
        this.url = url;
    }

    public String[] getUrlSepareted() {
        return this.url.split("/");
    }
}
