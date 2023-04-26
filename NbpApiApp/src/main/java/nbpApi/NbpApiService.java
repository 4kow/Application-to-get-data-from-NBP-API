package nbpApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NbpApiService
{
    private final RestTemplate restTemplate;

    public NbpApiService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public NbpCurrency getAverageExchangeRate(LocalDate date, String code) throws IOException
    {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date.toString() + "/" ;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK)
        {
            throw new IOException("Failed to get exchange rate");
        }
        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(body);
        ArrayNode ratesNode = (ArrayNode) rootNode.get("rates");
        String currency = rootNode.get("currency").asText();

        double mid = 0;

        for(JsonNode rateNode : ratesNode)
        {
            double avgRate = rateNode.get("mid").asDouble();
            mid += avgRate;
        }

        return new NbpCurrencyWithDate(code, date, mid, currency);
    }

    public NbpCurrency MaxMinExchangeVal(String code, int n) throws IOException
    {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/last/" + n + "/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() != HttpStatus.OK)
        {
            throw new IOException("Failed to get exchange rate stats");
        }
        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(body);
        ArrayNode ratesNode = (ArrayNode) rootNode.get("rates");
        String currency = rootNode.get("currency").asText();

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double sum = 0;

        for(JsonNode rateNode : ratesNode)
        {
            double avgRate = rateNode.get("mid").asDouble();
            max = Math.max(max, avgRate);
            min = Math.min(min, avgRate);
        }

        return new NbpCurrencyWithMaxMin(currency, code, max, min);
    }

    public List<NbpCurrencyWithDiff> BuyAskDiff(String code, int n) throws IOException
    {
        String url = "http://api.nbp.pl/api/exchangerates/rates/c/" + code + "/last/" + n + "/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if(response.getStatusCode() != HttpStatus.OK)
        {
            throw new IOException("Failed to get the difference information");
        }
        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(body);
        ArrayNode ratesNode = (ArrayNode) rootNode.get("rates");
        String currency = rootNode.get("currency").asText();

        double buy;
        double sell;
        double diff = 0;
        List<NbpCurrencyWithDiff> diffList = new ArrayList<>();

        for(JsonNode rateNode : ratesNode)
        {
            buy = rateNode.get("bid").asDouble();
            sell = rateNode.get("ask").asDouble();
            diff = Math.abs(buy - sell);
            String date = rateNode.get("effectiveDate").asText();
            diffList.add(new NbpCurrencyWithDiff(date, diff, currency, code));
        }
        return diffList;
    }
}
