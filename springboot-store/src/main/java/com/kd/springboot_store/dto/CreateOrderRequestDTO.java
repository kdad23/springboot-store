package com.kd.springboot_store.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequestDTO
{
    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
