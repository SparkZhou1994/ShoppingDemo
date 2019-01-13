package spark.shopping.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spark.shopping.controller.viewobject.UserVO;
import spark.shopping.error.BusinessException;
import spark.shopping.error.EmBusinessError;
import spark.shopping.response.CommonReturnType;
import spark.shopping.service.UserService;
import spark.shopping.service.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Spark
 * @Date 1/12/2019 3:22 AM
 * @Version 1.0
 **/
@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone) {
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将OTP验证码同对应用户的手机号关联,使用httpsession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        //将OTP验证码通过短信通道发送给用户,省去

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
