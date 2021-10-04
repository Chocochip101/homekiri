package com.example.homekiri.user.model;

import com.example.homekiri.config.Auditable;
import com.example.homekiri.survey.model.DessertSurvey;
import com.example.homekiri.survey.model.ExerciseSurvey;
import com.example.homekiri.survey.model.FoodSurvey;
import com.example.homekiri.survey.model.MediaSurvey;
import com.example.homekiri.user.dto.PostSignInReq;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
//외래키 preference랑 설정
@Entity
@Table(name="User")
@Setter
@Getter
@AllArgsConstructor
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    private String email;

    private String password;

    private String profileImg = null;

    private String nickName;

    @OneToOne(mappedBy = "user")
    private DessertSurvey dessertSurvey;

    @OneToOne(mappedBy = "user")
    private ExerciseSurvey exerciseSurvey;

    @OneToOne(mappedBy = "user")
    private FoodSurvey foodSurvey;

    @OneToOne(mappedBy = "user")
    private MediaSurvey mediaSurvey;



    public User(){}
    public User(PostSignInReq postSignInReq, String password){
        this.email = postSignInReq.getEmail();
        this.password = password;
        this.nickName = postSignInReq.getNickName();
    }

}
