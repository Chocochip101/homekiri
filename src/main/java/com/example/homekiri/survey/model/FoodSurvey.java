package com.example.homekiri.survey.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="FoodPreference")
public class FoodSurvey {
    @Id
    @Column(name = "idx")
    private Long idx;

    private Long userIdx;

    private int china;

    private int japan;

    private int western;

    private int korea;

    private int noodle;

    private int pork;

    private int beef;

    private int chicken;

    private int rice;

    private int seaFood;

    private int soup;

    private int temperature;

    private int raw;

    private int roasted;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedDate
    private LocalDateTime createdAt;
}
