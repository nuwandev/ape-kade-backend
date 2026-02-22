package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.Item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {

    List<Item> findAll(int page, int size, String sortBy, String direction);

    Item save(Item item);

    Optional<Item> findById(UUID id);

    Item update(Item item);

    void deleteById(UUID id);

    long countItems();
}

