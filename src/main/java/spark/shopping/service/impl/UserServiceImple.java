package spark.shopping.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import spark.shopping.dao.UserDOMapper;
import spark.shopping.dao.UserPasswordDOMapper;
import spark.shopping.dataobject.UserDO;
import spark.shopping.dataobject.UserPasswordDO;
import spark.shopping.error.BusinessException;
import spark.shopping.error.EmBusinessError;
import spark.shopping.service.UserService;
import spark.shopping.service.model.UserModel;
import spark.shopping.validator.ValidationResult;
import spark.shopping.validator.ValidatorImpl;

/**
 * @ClassName UserServiceImple
 * @Description TODO
 * @Author Spark
 * @Date 1/12/2019 3:28 AM
 * @Version 1.0
 **/
@Service
public class UserServiceImple implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }
        return userModel;
    }
    //数据有效性验证
/*    ValidationResult result = validator.validate(userModel);
    if (result.isHasErrors()) {
        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrorMsg());
    }*/
    //用户信息插入
/*    try{
        userDOMapper.insertSelective(userDO);
    }catch(DuplicateKeyException ex) {
        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复");
    }*/
}
