package org.example.boothello.service;

import org.example.boothello.bean.Welcome;
import org.example.boothello.exceptions.DuplicateException;
import org.example.boothello.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WelcomeService {
    ArrayList<Welcome> welcomeAL = new ArrayList<Welcome>();

    public Flux<Welcome> findAll() {
        System.out.println("[findAll] welcomeAL.size(): " + welcomeAL.size());
        Flux<Welcome> welcomeFlux = Flux.fromIterable(welcomeAL);
        System.out.println("here ....");
        List<Welcome> tmpL = welcomeAL.stream()
                .collect(Collectors.toList());
        System.out.println("[findAll] tmpL.size(): " + tmpL.size());
        return welcomeFlux;
    }

    public Mono<Welcome> create(Welcome welcome) {
        String from = welcome.getFrom();
        System.out.println("[create] from: " + from);
        List<Welcome> matchedList = welcomeAL.stream().filter(item -> item.getFrom().equalsIgnoreCase(from)).collect(Collectors.toList());
        if(matchedList.size() > 0) {
           throw new DuplicateException(welcome);
        }
        welcomeAL.add(welcome);
        return Mono.just(welcome);
    }

    public Boolean delete(String from) {
        System.out.println("[delete] from: " + from);
        List<Welcome> removableList = welcomeAL.stream().filter(item -> item.getFrom().equalsIgnoreCase(from)).collect(Collectors.toList());
        if(removableList.size() == 0) {
            throw new NotFoundException(from);
        }
        boolean removed = welcomeAL.removeAll(removableList);
        return removed;
    }

    public Mono<Welcome> saveUpdate(Welcome welcome) {
        String from = welcome.getFrom();
        System.out.println("[saveUpdate] from: " + from);
        List<Welcome> removableList = welcomeAL.stream().filter(item -> item.getFrom().equalsIgnoreCase(from)).collect(Collectors.toList());
        if(removableList.size() > 0) {
            welcomeAL.removeAll(removableList);
        }
        welcomeAL.add(welcome);
        return Mono.just(welcome);
    }

    public Mono<Welcome> update(String from, Welcome welcome) {
        System.out.println("[update] from: " + from);
        List<Welcome> updatableList = welcomeAL.stream().filter(item -> item.getFrom().equalsIgnoreCase(from)).collect(Collectors.toList());
        if(updatableList.size() == 0) {
            throw new NotFoundException(from);
        }
        return getWelcomeByFrom(from)
                .flatMap(w ->saveUpdate(welcome))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Welcome> getWelcomeByFrom(String from) {
        System.out.println("[getWelcomeByFrom] from: " + from);
        List<Welcome> l = welcomeAL.stream().filter(item -> item.getFrom().equalsIgnoreCase(from)).collect(Collectors.toList());
        System.out.println("[getWelcomeByFrom] : welcomeAL.size()" + welcomeAL.size());
        welcomeAL.forEach(System.out::println);
        System.out.println("[getWelcomeByFrom] : l.size()" + l.size());
        if(l.size() == 0) {
            System.out.println("[getWelcomeByFrom] here 1: " + from);
            return Mono.empty();
        }
        System.out.println("[getWelcomeByFrom] here 2: " + from);
        return Mono.just(welcomeAL.get(0));
    }
}
