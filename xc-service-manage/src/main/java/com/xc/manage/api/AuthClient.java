package com.xc.manage.api;

import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ShenHongSheng
 * ClassName: AuthClient
 * Description:
 * Date: 2020/12/7 16:10
 * @version V1.0
 */
@FeignClient(name = "xc-service-auth", path = "/auth/user")
public interface AuthClient {

    @GetMapping(value = "/getUserInfo")
    ResultInfo<UserVO> getUserInfo(@RequestParam("userName") String userName);

    @PostMapping(value = "/addUser")
    ResultInfo<String> addUser(@RequestBody UserVO userVO);
}
