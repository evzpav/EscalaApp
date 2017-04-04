package br.com.evandro.DTO;

import br.com.evandro.model.Store;

import java.util.List;

public class ListOfStoresDTO {
    private List<Store> listOfStores;

    public ListOfStoresDTO(List<Store> listOfStores) {
        this.listOfStores = listOfStores;
    }
}
