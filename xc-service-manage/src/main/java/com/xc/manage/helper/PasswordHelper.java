package com.xc.manage.helper;

import com.xc.common.domain.UserVO;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

/**
 * @author ShenHongSheng
 * ClassName: PasswordHelper
 * Description:
 * Date: 2020/12/21 14:47
 * @version V1.0
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "sha-256";

    private int hashIterations = 1000;

    /**
     *
     * @author kuangzhenhui
     * @version 2019/3/20
     * @param randomNumberGenerator :
     */
    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    /**
     *
     * @author kuangzhenhui
     * @version 2019/3/20
     * @param algorithmName :
     */
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     *
     * @author kuangzhenhui
     * @version 2019/3/20
     * @param hashIterations :
     */
    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     *
     * @author kuangzhenhui
     * @version 2019/3/20
     * @param user :
     */
    public void encryptPassword(UserVO user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        System.out.println("salt: " + user.getSalt());
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }
}