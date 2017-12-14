package utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

/**
 * Created by wangwn on 2016/5/4.
 */
public interface UserActionListener {

    void actionSuccess(AVUser avUser);
    void actionFailure(AVException e);
}
