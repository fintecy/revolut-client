package org.fintecy.md.revolut.serialization;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import org.fintecy.md.revolut.model.ExchangeRate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

public class RevolutModule extends SimpleModule {
    public static final String GROUP_ID = "org.fintecy.md";
    public static final String ARTIFACT_ID = "revolut-client";
    public static final Version VERSION = new Version(1, 0, 2, "SNAPSHOT",
            GROUP_ID, ARTIFACT_ID);

    public RevolutModule() {
        super(RevolutModule.class.getSimpleName(), VERSION,
                Map.of(ExchangeRate.class, ExchangeRateDeserializer.INSTANCE,
                        Instant.class, InstantDeserializer.INSTANT,
                        LocalDate.class, LocalDateDeserializer.INSTANCE));

        addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
    }
}
