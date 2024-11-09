package de.cityfeedback.service;

import de.cityfeedback.repository.StatusRepository;

public class StatusService {
    StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    
}
