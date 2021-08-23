package com.example.homekiri.recommendation.Dto.activity;

import com.example.homekiri.recommendation.model.activity.DessertActivity;
import com.example.homekiri.recommendation.model.activity.Info.DessertImage;
import com.example.homekiri.recommendation.model.activity.Info.Drink;
import lombok.Getter;

@Getter
public class DessertActivityResponseDto {
    private Long idx;
    private Long drinkIdx;
    private Long nonDrinkIdx;
    private String dessertName;
    private String description;
    private String imgUrl;

    public DessertActivityResponseDto(DessertActivity entity1, DessertImage entity2){
        this.idx = entity1.getIdx();
        this.drinkIdx = entity1.getDrinkIdx();
        this.nonDrinkIdx = entity1.getNonDrinkIdx();
        this.dessertName = entity1.getDessertName();
        this.description = entity1.getDescription();
        this.imgUrl = entity2.getImgUrl();
    }

}
