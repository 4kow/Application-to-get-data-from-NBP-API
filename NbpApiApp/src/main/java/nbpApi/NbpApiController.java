package nbpApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class NbpApiController
{
    private final NbpApiService nbpApiService;

    public NbpApiController(NbpApiService nbpApiService)
    {
        this.nbpApiService = nbpApiService;
    }

    @GetMapping("/axr")
    public ResponseEntity<NbpCurrency> getAverageExchangeRate(@RequestParam("date") String dateString,
                                                              @RequestParam("code") String code) throws IOException
    {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        NbpCurrency exchangeRate = nbpApiService.getAverageExchangeRate(date, code);
        return ResponseEntity.ok(exchangeRate);
    }

    @GetMapping("/min-max-average")
    public ResponseEntity<NbpCurrency> MaxMinExchangeVal(@RequestParam("code") String code,
                                                         @RequestParam("lastQuotations") int n)
    {
        try
        {
            NbpCurrency stats = nbpApiService.MaxMinExchangeVal(code, n);
            return ResponseEntity.ok(stats);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buy-sell-diff")
    public ResponseEntity<List<NbpCurrencyWithDiff>> BuyAskDiff(@RequestParam("code") String code,
                                                                @RequestParam("lastQuotations") int n)
    {
        {
            try
            {
                //NbpCurrency stats = nbpApiService.BuyAskDiff(code, n);
                List<NbpCurrencyWithDiff> diffList = nbpApiService.BuyAskDiff(code, n);
                //return ResponseEntity.ok(stats);
                return ResponseEntity.ok(diffList);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}


