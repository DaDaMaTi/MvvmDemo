package com.csmar.lib.base.util;

/**
 * 常量工具类
 */
public class Constants {
    /**
     * 用户信息保存文件名
     */
    public final static String PLATFORM_USER = "platform_user";
    /**
     * 平台用户id key
     */
    public final static String PLATFORM_USER_ID = "platform_user_id";
    /**
     * 平台用户名字
     */
    public final static String PLATFORM_USER_NAME = "platform_user_name";

    /**
     * 子系统用户账号
     */
    public final static String USER_ACCOUNT = "user_account";

    /**
     * 子系统用户手机号
     */
    public final static String USER_PHONE = "user_phone";

    /**
     * 子系统用户id
     */
    public final static String USER_ID = "user_id";

    /**
     * 用户商城，为了验证用户是否切换了账号
     */
    public final static String USER_MALL = "user_mall";

    /**
     * 是否从登录页面跳转， true 为是
     */
    public final static String ISLOGIN = "is_login";

    /**
     * 是否从忘记密码跳过去， true 为是
     */
    public final static String ISFORGET = "is_forget";

    // ------------------- 商品购物 -------------------
    public final static String TABLES_NAME_COLLEGE = "tables_name_college"; // 表名
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String PRICE = "price";
    public final static String NUMBER = "number"; // 商品添加数量
    public final static String IMAGENAME = "imageName";
    // ------------------- 商品购物 -------------------

    /**
     * 理财超市进入购买页面
     */
    public final static String LCMARKET = "lc_market";

    /**
     * 用户地址信息
     */
    public final static String SHR_NAME = "shr_name";
    public final static String SHR_PHONE = "shr_phone";
    public final static String SHR_ADDRESS = "shr_address";

    /**
     * 保存用户总资产是否，用户id拼接该字段 作为 文件名
     */
    public final static String USERID_EYE = "_eye";
    public final static String EYE_ASSETS = "eye_assets";

    /**
     * 一页数据大小
     */
    public final static int PAGE_SIZE = 60;

    /**
     * 从主页中 理财 进入 赚赚   赚赚成功转入转出
     */
    public final static String FINANCIAL_ZZ = "financial_zz";

    /**
     * Rxbus 购买理财或赎回
     */
    public final static String FINANCIAL_BUY_SH = "financial_buy_sh";
}
