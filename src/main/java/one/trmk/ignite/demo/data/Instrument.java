package one.trmk.ignite.demo.data;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.Date;
import java.util.Objects;

public class Instrument {
    private final String id;

    @QuerySqlField(index = true)
    private final InstrumentAttributes attributes;
    private final Integer version;
    private final Date lastUpdated;

    public Instrument(String id, InstrumentAttributes attributes, Integer version, Date lastUpdated) {
        this.id = id;
        this.attributes = attributes;
        this.version = version;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public InstrumentAttributes getAttributes() {
        return attributes;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instrument that = (Instrument) o;
        return Objects.equals(id, that.id) && Objects.equals(attributes, that.attributes) && Objects.equals(version, that.version) && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributes, version, lastUpdated);
    }
}
