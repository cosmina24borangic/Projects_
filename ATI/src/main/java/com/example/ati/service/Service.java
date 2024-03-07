package com.example.ati.service;

import com.example.ati.domain.Pacient;
import com.example.ati.domain.Pat;
import com.example.ati.domain.TipPat;
import com.example.ati.domain.exceptions.EmptyStringException;
import com.example.ati.domain.exceptions.NegativeNumberException;
import com.example.ati.repository.Repository;
import com.example.ati.utils.events.ChangeEventType;
import com.example.ati.utils.events.PacientChangeEvent;
import com.example.ati.utils.observer.Observable;
import com.example.ati.utils.observer.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Service {
    private final Repository<Integer, Pacient> pacientRepository;
    private final Repository<Integer, Pat> patRepository;

    private List<Observer<PacientChangeEvent>> pacientObserver = new ArrayList<>();

    public Service(Repository<Integer, Pacient> pacientRepository, Repository<Integer, Pat> patRepository) {
        this.pacientRepository = pacientRepository;
        this.patRepository = patRepository;
    }

    public List<Pacient> getAllPacienti() {
        List<Pacient> pacients = new ArrayList<>();
        this.pacientRepository.findAll().forEach(pacients::add);
        return pacients;
    }

    public List<Pat> getAllPaturi() {
        List<Pat> pats = new ArrayList<>();
        this.patRepository.findAll().forEach(pats::add);
        return pats;
    }

    public Boolean findPacientInPat(Pacient pacient) {
        for (Pat pat : getAllPaturi()) {
            if (Objects.equals(pat.getPacient(), pacient.getId())) {
                return true;
            }
        }
        return false;
    }

    public Iterable<Pacient> sortPacientAsteptareByGravitate() {
        List<Pacient> pacients = new ArrayList<>();
        for (Pacient pacient : this.pacientRepository.findAll()) {
            if (!findPacientInPat(pacient)) {
                pacients.add(pacient);
            }
        }
        //TODO: SORTARE
        return pacients;
    }

    public void assignPatToPacient(Integer pacient, String category) throws EmptyStringException, NegativeNumberException {
        for (Pat pat : getAllPaturi()) {
            if (TipPat.valueOf(category) == pat.getTip() && !(pat.getPacient() >= 1)) {
                pat.setPacient(pacient);
                patRepository.update(pat, pat);
                this.notifyObservers(new PacientChangeEvent(ChangeEventType.UPDATE, null));
                return;
            }
        }
    }

    public void addObserver(Observer<PacientChangeEvent> e) {
        pacientObserver.add(e);
    }

    public void removeObserver(Observer<PacientChangeEvent> e) {
        pacientObserver.remove(e);
    }

    public void notifyObservers(PacientChangeEvent p) {
        pacientObserver.stream().forEach(x -> x.update(p));
    }
}