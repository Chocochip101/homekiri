package com.example.homekiri.recommendation.Dto.ActivityRecommendDto;

import com.example.homekiri.dessert.model.DessertActivity;
import lombok.Getter;

@Getter
public class DessertRecommendDto {
    private Long idx;
    private String dessertName;
    private String Category;
    private String imgUrl;

    public DessertRecommendDto(DessertActivity entity1){
        this.idx = entity1.getIdx();
        this.dessertName = entity1.getDessertName();
        if(entity1.getNonDrinkIdx() == -1)
            this.Category = "음료";
        else
            this.Category = "디저트";
        this.imgUrl = entity1.getDessertImage().getImgUrl();
    }
}
