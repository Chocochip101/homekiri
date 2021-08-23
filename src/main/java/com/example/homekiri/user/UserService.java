package com.example.homekiri.user;

import com.example.homekiri.config.BaseException;
import com.example.homekiri.config.BaseResponseStatus;
import com.example.homekiri.config.secret.Secret;
import com.example.homekiri.library.AES128;
import com.example.homekiri.library.JwtService;
import com.example.homekiri.user.dto.PostLogInRes;
import com.example.homekiri.user.model.User;
import com.example.homekiri.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    /**
      Password encode
      @param String password
      @return String
    */
    public String encodePassWord(String password) throws BaseException{
        AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
        try {
            String result = aes128.encrypt(password);
            System.out.println("[Service] password encode complete");
            return result;
        }catch(Exception e){
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }
    }

    /**
    validateEmail service
    @param String password
    @return boolean
     */
    public void validateEmail(String email) throws BaseException{
        if(userRepository.findByEmail(email).isEmpty()==false){
            throw new BaseException(BaseResponseStatus.DUPLICATE_USER_EMAIL);
        }
    }

    public void validateNickname(String nickname) throws BaseException{
        if(userRepository.findByNickname(nickname).isEmpty()==false){
            throw new BaseException(BaseResponseStatus.DUPLICATE_USER_NICKNAME);
        }
    }


    /**
    SignIn service
    @param String password
    @return Long
     */
    public Long signIn(User user) throws BaseException{

        try {
            //validate userEmail
            validateEmail(user.getEmail());
            //validate nickName
            validateNickname(user.getNickName());
            return userRepository.save(user);
        }catch (BaseException e){
            throw new BaseException(e.getStatus());
        }

    }

    /**
     로그인 service
     @param String email String password
     @return PostLogInRes
     */
    public PostLogInRes logIn(String email, String password) throws BaseException{
        AES128 aes128 = new AES128(Secret.USER_INFO_PASSWORD_KEY);
        User user;
        try {
            user = userRepository.findSingleUserByEmail(email);
        }catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        // 패스워드 decrypt
        Boolean pwMatch;
        try{
            pwMatch = aes128.decrypt(user.getPassword()).equals(password);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.PASSWORD_DECRYPTION_ERROR);
        }

        // 패스워드 비교
        if (!pwMatch) {
            throw new BaseException(BaseResponseStatus.PASSWORD_NOT_MATCH);
        }

        String jwt = jwtService.createJwt(user.getIdx());
        return new PostLogInRes(user.getIdx(), jwt);

    }

}
