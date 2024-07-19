import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;

public class NasaHttpClient {
    private final String security = "P3Iy21y4meq9jKgQ2NLxtzwE0b0PtGuDvLdIJHFL";
    private final String API_URL = "https://api.nasa.gov/planetary/apod";

    protected NasaHttpClientParams httpClientParams;
    protected NasaAnswer answer;

    public final CloseableHttpClient httpClient;

    protected void makeAnswer() {
        HttpGet request = new HttpGet(this.API_URL + this.httpClientParams.getSerializeParams());
        ObjectMapper mapper = new ObjectMapper();

        try {
            CloseableHttpResponse response = this.httpClient.execute(request);
            this.answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected CloseableHttpResponse getImageResponse()
    {
        HttpGet imageRequest = new HttpGet(answer.url);
        try {
            return this.httpClient.execute(imageRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void saveFile(@NotNull CloseableHttpResponse response) {
        String[] urlSepareted = this.answer.getUrlSepareted();
        String filename = urlSepareted[urlSepareted.length - 1];
        try {
            FileOutputStream fos = new FileOutputStream("images/" + filename);
            response.getEntity().writeTo(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NasaHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void downloadImage(
            String date,
            String startDate,
            String endDate,
            Integer count,
            Boolean thumbs
    ) {
        this.httpClientParams = new NasaHttpClientParams(this.security, date, startDate, endDate, count, thumbs);
        this.makeAnswer();
        CloseableHttpResponse imageResponse = this.getImageResponse();
        this.saveFile(imageResponse);
    }
}
