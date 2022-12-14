package com.forDukwoo.timeZip.user;

import com.forDukwoo.timeZip.config.BaseException;
import com.forDukwoo.timeZip.user.model.GetScrapRes;
import com.forDukwoo.timeZip.user.model.PostUserReq;
import com.forDukwoo.timeZip.user.model.PostUserRes;
import com.forDukwoo.timeZip.utils.JwtService;
import com.forDukwoo.timeZip.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forDukwoo.timeZip.config.BaseResponseStatus.*;

@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        String pwd;
        // 중복 처리
        if (userProvider.checkIdExist(postUserReq.getEmail()) == 1) {
            throw new BaseException(USERS_DUPLICATED_ID);
        }
        if (userProvider.checkNickExist(postUserReq.getNick()) == 1) {
            throw new BaseException(USERS_DUPLICATED_NICK);
        }
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPwd());
            postUserReq.setPwd(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userId = userDao.createUser(postUserReq);
            String jwt = jwtService.createJwt(userId);
            return new PostUserRes(jwt, userId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteScrap(int userId, int scrapId) throws BaseException{
        // scrapId 가 존재하지 않는 경우
        if(userProvider.checkScrapIdExist(scrapId) == 0) {
            throw new BaseException(POSTS_EMPTY_POST_ID);
        }
        // scrapId userId의 쌍이 존재하지 않는 경우
        if(userProvider.checkDuplicateScrap(scrapId, userId) == 0) {
            throw new BaseException(EMPTY_SCRAP);
        }
        try {
            userDao.deleteScrap(userId, scrapId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
