package one.trmk.ignite.demo.data;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.Objects;

public class InstrumentAttributes {

    private final String name;
    private final String type;

    @QuerySqlField(index = true)
    private final String symbol;
    private final String currency;
    private final String exchange;
    private final String country;
    private final String sector;
    private final String industry;
    private final String subIndustry;
    private final String description;

    public InstrumentAttributes(String name, String type, String symbol, String currency, String exchange, String country, String sector, String industry, String subIndustry, String description) {
        this.name = name;
        this.type = type;
        this.symbol = symbol;
        this.currency = currency;
        this.exchange = exchange;
        this.country = country;
        this.sector = sector;
        this.industry = industry;
        this.subIndustry = subIndustry;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }
    public String getIndustry() {
        return industry;
    }

    public String getCurrency() {
        return currency;
    }

    public String getExchange() {
        return exchange;
    }

    public String getSubIndustry() {
        return subIndustry;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentAttributes that = (InstrumentAttributes) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(symbol, that.symbol) && Objects.equals(currency, that.currency) && Objects.equals(exchange, that.exchange) && Objects.equals(country, that.country) && Objects.equals(sector, that.sector) && Objects.equals(industry, that.industry) && Objects.equals(subIndustry, that.subIndustry) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, symbol, currency, exchange, country, sector, industry, subIndustry, description);
    }

    @Override
    public String toString() {
        return "InstrumentAttributes{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currency='" + currency + '\'' +
                ", exchange='" + exchange + '\'' +
                ", country='" + country + '\'' +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", subIndustry='" + subIndustry + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

