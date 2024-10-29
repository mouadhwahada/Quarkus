package org.stock.dto;

import java.time.LocalDateTime;

public class SaleRequestDto {

    private Long pieceId;
    private String pieceName;
    private String clientCin;
    private LocalDateTime saleDate;
    private Integer quantitySold;

    // Constructeur avec tous les champs
    public SaleRequestDto(Long pieceId, String pieceName, String clientCin, LocalDateTime saleDate, Integer quantitySold) {
        this.pieceId = pieceId;
        this.pieceName = pieceName;
        this.clientCin = clientCin;
        this.saleDate = saleDate;
        this.quantitySold = quantitySold;
    }

    // Getters et Setters
    public Long getPieceId() {
        return pieceId;
    }

    public void setPieceId(Long pieceId) {
        this.pieceId = pieceId;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }
}
