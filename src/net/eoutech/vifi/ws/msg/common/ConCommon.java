package net.eoutech.vifi.ws.msg.common;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class ConCommon {
    public static final String task_img = "TASK_IMG";
    public static final String location_tel = "LOCATION_TEL";
    public static final String req_code_traffic = "REQCODE_TRAFFIC";
    public static final String init_traffic = "INIT_TRAFFIC";
    public static final String month_traffic = "MONTH_TRAFFIC";
    public static final String share_traffic = "SHARE_TRAFFIC";
    public static final String share_effect_time = "SHARE_EFFECT_TIME";
    public static final String init_effect_time = "INIT_EFFECT_TIME";
    public static final String activity_status = "ACTIVITY_STATUS";
    public static final String default_avatar = "DEFAULT_AVATAR";
    public static final String task_special = "TASK_SPECIAL";
    public static final String card_status_change = "CARD_STATUS_CHANGE";
    public static final String server_ip_port = "SERVER_IP_PORT";
    public static final String sign_points = "SIGN_POINTS";//日常签到积分
    public static final String attendance_bonus_points = "ATTENDANCE_BONUS_POINTS";//额外签到积分
    public static final String redeem_flow_effective_time = "REDEEM_FLOW_EFFECTIVE_TIME";//积分兑换流量到期时间
    public static final String apple_review = "APPLE_REVIEW";//苹果审核阶段，屏蔽MAC地址
    public static final String channel_minute = "CHANNEL_MINUTE";//定时器推延时间
    public static final String redis_time = "REDIS_TIME";//存储流量数据时间
    public static final String flow_record = "FLOW_RECORD";//流量入库标准
    public static final String invitation_code_provider = "INVITATION_CODE_PROVIDER";//提供邀请码的用户所得积分
    public static final String invitation_code_recipient = "INVITATION_CODE_RECIPIENT";//使用邀请码的用户所得积分
    public static final String rest_net_data = "REST_NET_DATA";//sim卡总流量（单位G）
    public static final String net_proportion="NET_PROPORTION";//卡限制流量比
    public static final String system_default_avatar="SYSTEM_DEFAULT_AVATAR";//系统默认头像
    public static final String redeem_times="REDEEM_TIMES";//积分兑换-可兑换次数
    public static final String token_time="TOKEN_TIME";//token在redis中存放的时间
    public static final String mac_token_time="MAC_TOKEN_TIME";//mac和token对应的关系在redis中存放的时间
    public static final String auto_push_card="AUTO_PUSH_CARD";//自动推卡
    public static final String timing_test="TIMING_TEST";//定时上下卡测试
}
