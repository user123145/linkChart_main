package com.fourward.linkchart.service.impl;

import com.fourward.linkchart.dto.UserInfoDTO;
import com.fourward.linkchart.persistence.mapper.IUserInfoMapper;
import com.fourward.linkchart.service.IUserInfoService;
import com.fourward.linkchart.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {
    private final IUserInfoMapper userInfoMapper;

    @Transactional
    @Override
    public void insertUserInfo(UserInfoDTO pDTO) {
        userInfoMapper.insertUserInfo(pDTO);
    }

    @Override
    public UserInfoDTO getUserLoginCheck(UserInfoDTO pDTO) {

        return userInfoMapper.getUserLoginCheck(pDTO);
    }

    @Override
    public UserInfoDTO checkUserIdExist(UserInfoDTO pDTO) {

        return userInfoMapper.getUserIdExist(pDTO);
    }

    @Override
    public UserInfoDTO checkUserEmailExist(UserInfoDTO pDTO) {

        return userInfoMapper.getUserEmailExist(pDTO);
    }

    @Override
    public void updateUserPsw(UserInfoDTO pDTO) {
        userInfoMapper.updateUserPsw(pDTO);
    }

    @Override
    public void updateUserEmail(UserInfoDTO pDTO) {
        userInfoMapper.updateUserEmail(pDTO);
    }

    @Override
    public void updateUserAddr(UserInfoDTO pDTO) {
        userInfoMapper.updateUserAddr(pDTO);
    }

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) {
        UserInfoDTO rDTO = userInfoMapper.getUserInfo(pDTO);
        try {
            rDTO.setUser_email(EncryptUtil.decAES128CBC(rDTO.getUser_email()));
            rDTO.setUser_addr(EncryptUtil.decAES128CBC(rDTO.getUser_addr()));
        } catch (Exception ignored) {
            return null;
        }

        return rDTO;
    }
}