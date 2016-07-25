package com.eexit;

public class ApiPaths {
    public static final String PI_ID = "pi_id";

    public static final String SERVER = "http://192.168.0.40:3000";

    public static final String SPLASH_PATH = "/api/v1/splash/";
    public static final String LOGIN_EMAIL_PATH = "/api/v1/users/login/";
    public static final String SEND_ACCESS_TOKEN_PATH = "/api/v1/users/google/";
    public static final String KAKAO_SEND_ACCESS_TOKEN_PATH = "/api/v1/users/kakao/";
    public static final String FACEBOOK_SEND_ACCESS_TOKEN_PATH = "/api/v1/users/facebook/";
    public static final String SIGNUP_EMAIL_PATH = "/api/v1/users/";

    public static final String PIS_PATH = "/pis/";
    public static final String PI_DETAIL_PATH = "/pis/{" + PI_ID + "}";
    public static final String CHANGE_DIRECTION_PATH = "/pis/{" + PI_ID + "}/direction";
    public static final String CHANGE_DESTINATION_PATH = "/pis/{" + PI_ID + "}/destination";
    public static final String CHANGE_STATE_PATH = "/pis/{" + PI_ID + "}/state";
    public static final String CHANGE_MESSAGE_PATH = "/pis/{" + PI_ID + "}/message";
}
