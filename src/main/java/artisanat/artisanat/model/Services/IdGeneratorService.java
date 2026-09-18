package artisanat.artisanat.model.Services;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {

    private AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public Long generateId() {
        return counter.incrementAndGet();
    }
}
