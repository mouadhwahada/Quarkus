package org.stock.Entities;

import java.io.Serializable;
import java.util.Objects;

public class PieceSaleId implements Serializable {

    private String clientCin;
    private Integer purchaseNumber;

    public PieceSaleId() {
    }

    public PieceSaleId(String clientCin, Integer purchaseNumber) {
        this.clientCin = clientCin;
        this.purchaseNumber = purchaseNumber;
    }

    // Getters and Setters

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceSaleId that = (PieceSaleId) o;
        return Objects.equals(clientCin, that.clientCin) &&
                Objects.equals(purchaseNumber, that.purchaseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientCin, purchaseNumber);
    }
}
