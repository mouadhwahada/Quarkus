package org.stock.dto;

public class InterventionRequest {
        private InterventionHistorydto interventionDTO;
        private Integer purchaseNumber;

        // Getters and Setters
        public InterventionHistorydto getInterventionDTO() {
            return interventionDTO;
        }

        public void setInterventionDTO(InterventionHistorydto interventionDTO) {
            this.interventionDTO = interventionDTO;
        }

        public Integer getPurchaseNumber() {
            return purchaseNumber;
        }

        public void setPurchaseNumber(Integer purchaseNumber) {
            this.purchaseNumber = purchaseNumber;
        }
    }


