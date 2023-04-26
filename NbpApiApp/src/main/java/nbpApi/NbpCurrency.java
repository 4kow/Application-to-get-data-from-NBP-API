package nbpApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NbpCurrency
{
    private String currency;
    private String code;

    public NbpCurrency(String code, String currency)
    {
        this.code = code;
        this.currency = currency;
    }

    public NbpCurrency() {}

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}

class NbpCurrencyWithDate extends NbpCurrency
{
    private LocalDate date;
    private double mid;

    public NbpCurrencyWithDate(String code, LocalDate date, double mid, String currency)
    {
        super(code, currency);
        this.date = date;
        this.mid = mid;
    }

    public NbpCurrencyWithDate(String currency, String code, LocalDate date, double mid) {}

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public double getMid()
    {
        return mid;
    }

    public void setMid(double mid)
    {
        this.mid = mid;
    }
}

class NbpCurrencyWithMaxMin extends NbpCurrency
{
    private double max;
    private double min;

    public NbpCurrencyWithMaxMin(String currency, String code, double max, double min)
    {
        super(code, currency);
        this.max = max;
        this.min = min;
    }

    public NbpCurrencyWithMaxMin(String currency,  double max, String code, double min) {}

    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getMin()
    {
        return min;
    }

    public void setMin(double min)
    {
        this.min = min;
    }
}

class NbpCurrencyWithDiff extends NbpCurrency
{
    private String date;
    private double diff;

    public NbpCurrencyWithDiff(String date, double diff, String currency, String code)
    {
        super(code, currency);
        this.date = date;
        this.diff = diff;
    }
    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
