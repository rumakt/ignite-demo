package one.trmk.ignite.demo.rest;

import one.trmk.ignite.demo.data.Instrument;
import one.trmk.ignite.demo.data.InstrumentAttributes;
import one.trmk.ignite.demo.data.InstrumentUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.IndexQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.cache.Cache;

import static org.apache.ignite.cache.query.IndexQueryCriteriaBuilder.eq;

@RestController
@RequestMapping("/instruments")
public class InstrumentController {

    private final Ignite ignite;
    private final TaskExecutor executor;

    public InstrumentController(Ignite ignite,
    TaskExecutor taskExecutor) {
        this.ignite = ignite;
        this.executor = taskExecutor;
        initCache();
    }

    @RequestMapping(path = "/{id}")
    public Mono<Instrument> getInstrument(@PathVariable String id) {
        return Mono.justOrEmpty(getCache().get(id));
    }

    @RequestMapping(path = "/symbol/{symbol}")
    public Flux<Instrument> getBySymbol(@PathVariable String symbol) {
        var result = getCache().query(new IndexQuery<String, Instrument>(Instrument.class, "INSTRUMENTATTRIBUTES_SYMBOL_IDX")
                .setCriteria(eq("symbol", symbol)));

        return Flux.fromIterable(result)
                .mapNotNull(Cache.Entry::getValue);
    }

    @RequestMapping(path = "/scan/{symbol}")
    public Flux<Instrument> getBySymbolScan(@PathVariable String symbol) {
        var cursor = getCache().query(new ScanQuery<>(
                        (IgniteBiPredicate<String, Instrument>) (s, instrument) -> instrument.getAttributes().getSymbol().equals(symbol)));

        return Flux.fromIterable(cursor)
                .mapNotNull(Cache.Entry::getValue);
    }

    private IgniteCache<String, Instrument> getCache() {
        return ignite.getOrCreateCache("instrument");
    }

    private void initCache() {
        executor.execute(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ignite.cluster().state(ClusterState.ACTIVE);
            getCache().put("AAPL", InstrumentUtils.createInstrument("1", "AAPL"));
            getCache().put("MSFT", InstrumentUtils.createInstrument("2", "MSFT"));

            for(int i=0; i< 10_000_000; i++) {
                getCache().put("AMZN" + i, InstrumentUtils.createInstrument(String.valueOf(i), "AMZN" + i));
                if(i % 10000 == 0) {
                    System.out.println("Added " + i + " records");
                }
            }
        });
    }
}
