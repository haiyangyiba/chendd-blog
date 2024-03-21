package cn.chendd.blog.admin.system.home.controller;

import cn.chendd.blog.base.controller.BaseController;
import cn.chendd.core.common.constant.Constant;
import cn.chendd.core.utils.GifImageUtil;
import cn.chendd.core.utils.GifVerificationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码
 *
 * @author chendd
 * @date 2020/5/24 12:10
 */
@Api(value = "登录验证码" , tags = "系统内登录gif验证码")
@ApiSort(40)
@Controller
@RequestMapping("/verificationCode")
public class VaildateCodeController extends BaseController {

    /**
     * 生成验证码的长度
     */
    private static final Integer CODE_LENS = 4;
    /**
     * 生成验证码的源字符，去掉大写字符O和小写字母l和数字4
     */
    private static final String RANDOM_CHAR  = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23567890";

    @GetMapping(value = "/test" , produces = MediaType.IMAGE_GIF_VALUE)
    @ApiOperation(value = "验证码" , notes = "生成gif验证码")
    @ResponseBody
    public BufferedImage bigImage() throws IOException {
        //生成 N 位长度的随机数，做为验证码
        char[] randCodes = new char[CODE_LENS];
        for(int i = 0 ; i < CODE_LENS ; i ++){
            randCodes[i] = getRandomChar();
        }
        try (OutputStream bos = response.getOutputStream()) {
            BufferedImage image = GifVerificationCodeUtil.createVaildateCodeImage(bos , randCodes);
            session.setAttribute(Constant.LOGIN_VALIDATE_KEY, new String(randCodes));
            return image;
        }
    }

    @GetMapping(produces = MediaType.IMAGE_GIF_VALUE)
    @ApiOperation(value = "验证码" , notes = "生成gif验证码")
    @ResponseBody
    public BufferedImage image() throws IOException {
        // 输出图象到页面
        return this.createImage();
    }

    private BufferedImage createImage() throws IOException {
        //生成 N 位长度的随机数，做为验证码
        char[] randCodes = new char[CODE_LENS];
        for(int i = 0 ; i < CODE_LENS ; i ++){
            randCodes[i] = getRandomChar();
        }
        try (OutputStream bos = response.getOutputStream()) {
            BufferedImage image = GifImageUtil.createVaildateCodeImage(bos , randCodes);
            session.setAttribute(Constant.LOGIN_VALIDATE_KEY, new String(randCodes));
            return image;
        }
    }

    /**
     * 获取一定长度的随机数
     */
    private Character getRandomChar(){
        int lens = RANDOM_CHAR.length();
        int randomInt = new Random().nextInt(lens);
        return RANDOM_CHAR.charAt(randomInt);
    }

}
