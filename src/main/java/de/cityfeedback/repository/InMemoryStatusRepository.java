package de.cityfeedback.repository;

import de.cityfeedback.domain.Status;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStatusRepository implements StatusRepository {
    private final Map<Long, Status> statusDatabase = new HashMap<>();

    public InMemoryStatusRepository() {
        this.initStatusDb();
    }

    private void initStatusDb() {
        Status offen = new Status(1L, "OFFEN");
        Status inBearbeitung = new Status(1L, "IN BEARBEITUNG");
        Status closed = new Status(1L, "ABGESCLOSSEN");
        statusDatabase.put(offen.getId(), offen);
        statusDatabase.put(inBearbeitung.getId(), inBearbeitung);
        statusDatabase.put(closed.getId(), closed);
    }
}
