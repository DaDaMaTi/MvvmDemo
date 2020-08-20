package com.csmar.mvvmdemo.api;

import com.csmar.lib.net.RequestApi;
import com.csmar.mvvmdemo.bean.PlatformResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi{
    /**
     * 平台登录api
     *
     * @param username 平台账号
     * @param pwd      平台密码
     * @return
     */
    @FormUrlEncoded
    @POST("{url}thirdHJLogin")
    Observable<PlatformResponse> platformLogin(@Path(value = "url", encoded = true) String path,
                                               @Field("userName") String username,
                                               @Field("password") String pwd);
}
