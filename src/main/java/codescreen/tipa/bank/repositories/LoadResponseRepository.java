package codescreen.tipa.bank.repositories;

import codescreen.tipa.bank.model.LoadResponse;

public interface LoadResponseRepository {
    LoadResponse addToHistory(LoadResponse response);
}
