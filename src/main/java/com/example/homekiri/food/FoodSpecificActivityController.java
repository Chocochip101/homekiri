package com.example.homekiri.food;

import com.example.homekiri.config.BaseException;
import com.example.homekiri.config.BaseResponse;
import com.example.homekiri.config.BaseResponseStatus;
import com.example.homekiri.food.Dto.FoodActivityResponseDto;
import com.example.homekiri.food.service.FoodActivityDetailsService;
import com.example.homekiri.library.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/web/details")
public class FoodSpecificActivityController {
    private final JwtService jwtService;
    private final FoodActivityDetailsService foodActivityDetailsService;

    /**
     JWT 인증 메서드
     @param String JWT
     @return BOOLEAN
     */
    public boolean jwtAuth(Long userIdx) throws BaseException{
        try {
            Long jwtUserIdx = this.jwtService.getUserIdx();
            if(jwtUserIdx == userIdx){
                return true;
            }
            else{
                return false;
            }
        }catch (BaseException e){
            throw new BaseException(e.getStatus());
        }

    }

    /**
     * 음식 상세 설명 API
     * [GET] /web/details/{userIdx}/food/{foodIdx}
     * @param Long foodIdx, userIdx
     * @return BaseResponse<FoodActivityResponseDto>
     * if (Activity Index not exist) Throw INVALID_ACTIVITY_IDX
     */
    @ResponseBody
    @GetMapping("/{userIdx}/food/{foodIdx}")
    public BaseResponse<FoodActivityResponseDto> returnFoodActivity(@PathVariable Long foodIdx, @PathVariable Long userIdx){
        //jwt 인증
        try {
            if (!jwtAuth(userIdx)) {
                throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
            }
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
        try{
            FoodActivityResponseDto result = foodActivityDetailsService.findById(foodIdx);
            return new BaseResponse<>(result);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}