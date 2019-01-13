package spark.shopping.service;

import spark.shopping.service.model.UserModel;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author Spark
 * @Date 1/12/2019 3:28 AM
 * @Version 1.0
 **/
public interface UserService {
    UserModel getUserById(Integer id);
}
