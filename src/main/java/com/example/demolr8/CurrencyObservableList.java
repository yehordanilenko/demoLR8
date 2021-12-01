package com.example.demolr8;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CurrencyObservableList {
    @JsonProperty("metas")
    final
    ObservableList<BankCurrency> metas;

    public CurrencyObservableList() {
        metas = FXCollections.observableArrayList();
    }
    public ObservableList<BankCurrency> getMetas() {
        return metas;
    }

    public void add(BankCurrency meta) {
        this.metas.add(meta);
    }


    @Override
    public String toString() {
        return "Metas{" +
                "metas=" + metas +
                '}';
    }
}