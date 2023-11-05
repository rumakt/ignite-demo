package one.trmk.ignite.demo.data;

import java.util.Date;

public class InstrumentUtils {

    public static Instrument createInstrument(String id, String symbol) {
        InstrumentAttributes attributes = createAttributes(symbol);
        return new Instrument(id, attributes, 1, new Date());
    }


    private static InstrumentAttributes createAttributes(String symbol) {
        return new InstrumentAttributes(null,
                null,
                symbol,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
