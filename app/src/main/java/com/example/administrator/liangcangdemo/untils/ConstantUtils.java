package com.example.administrator.liangcangdemo.untils;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ConstantUtils {
    //首頁
    public static final String SHOP_HOME = "http://mobile.iliangcang.com/goods/newShopHome?app_key=Android&sig=3780CB0808528F7CE99081D295EE8C0F%7C116941220826768&uid=626138098&user_token=0516ed9429352c8e1e3bd11c63ba6f54&v=1.0";
    //专题&page=1
    public static final String SHOP_SPECIAL = "http://mobile.iliangcang.com/goods/shopSpecial?app_key=Android&count=10&sig=3780CB0808528F7CE99081D295EE8C0F%7C116941220826768&uid=626138098&user_token=0516ed9429352c8e1e3bd11c63ba6f54&v=1.0";
    //分类
    public static final String SHOP_TYPE = "http://mobile.iliangcang.com/goods/goodsCategory?app_key=Android&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";
    //品牌 &page=1
    public static final String SHOP_BRAND = "http://mobile.iliangcang.com/brand/brandList?app_key=Android&count=20&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";
    //分类-listitem
    public static final String SHOP_TYPE_START = "http://mobile.iliangcang.com/goods/goodsShare?app_key=Android&cat_code=";
    public static final String SHOP_TYPE_END = "&count=10&coverId=1&page=1&sig=3D3968703BE211CC6D931C9D4F737FB4%7C290216330933368&v=1.0";
    //&page=1
    public static final String SHOP_TYPE_END_NOPAGE = "&count=10&coverId=1&sig=3D3968703BE211CC6D931C9D4F737FB4%7C290216330933368&v=1.0";

    //商品详情页
    public static final String SHOP_TYPE_DETAIL_START = "http://mobile.iliangcang.com/goods/goodsDetail?app_key=Android&goods_id=";
    public static final String SHOP_TYPE_DETAIL_END = "&sig=BF287AF953103F390674E73DDA18CFD8%7C639843030233268&v=1.0";
    public static final String SHOP_TYPE_DETAIL = "http://mobile.iliangcang.com/goods/goodsDetail?app_key=Android&sig=BF287AF953103F390674E73DDA18CFD8%7C639843030233268&v=1.0";
    //品牌详情页  &brand_id=841
    public static final String SHOP_BRAND_DETAIL = "http://mobile.iliangcang.com/brand/brandShopList?app_key=Android&count=20&page=1&sig=430BD99E6C913B8B8C3ED109737ECF15%7C830952120106768&v=1.0";
    //礼物
    public static final String SHOP_GIFT_1 = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=7&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0";
    public static final String SHOP_GIFT_FESTIVAL = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=1&page=1&sig=DFD7151CC9D607E396FE108FE270FFF3%7C366534120395468&v=1.0";
    public static final String SHOP_GIFT_LOVE = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=2&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0";
    public static final String SHOP_GIFT_BRITHDAY = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=3&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0";
    public static final String SHOP_GIFT_FRIEND = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=4&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0";
    public static final String SHOP_GIFT_CHILD = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=5&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0";
    public static final String SHOP_GIFT_PARENT = "http://mobile.iliangcang.com/goods/goodsList?app_key=Android&count=10&list_id=6&page=1&sig=73760B2740FA36D5A273523FBC9295FE%7C285269230036268&v=1.0";
    //达人首页(默认推荐)&page=1
    public static final String DAREN_HOME = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&sig=BF287AF953103F390674E73DDA18CFD8|639843030233268&v=1.0";
    //粉丝 &owner_id=999998864
    public static final String DAREN_DETAIL_FOLLOW = "http://mobile.iliangcang.com/user/masterFollowed?app_key=Android&count=12&page=1&sig=BF287AF953103F390674E73DDA18CFD8%7C639843030233268&v=1.0";
    //喜欢&owner_id=121530857
    public static final String DAREN_DETAIL_LIKE = "http://mobile.iliangcang.com/user/masterLike?app_key=Android&count=12&page=1&sig=BF287AF953103F390674E73DDA18CFD8|639843030233268&v=1.0";
    //推荐&owner_id=121530857
    public static final String DAREN_DETAIL_RECOMMEND = "http://mobile.iliangcang.com/user/masterListInfo?app_key=Android&count=10&page=1&sig=BF287AF953103F390674E73DDA18CFD8%7C639843030233268&v=1.0";
    //关注&owner_id=121530857
    public static final String DAREN_DETAIL_GUANZHU = "http://mobile.iliangcang.com/user/masterFollow?app_key=Android&count=12&page=1&sig=BF287AF953103F390674E73DDA18CFD8|639843030233268&v=1.0";
    /**
     * 百思不得姐 推荐
     */
    public static final String BS_RECOMMEND = "http://s.budejie.com/topic/list/jingxuan/1/budejie-android-6.6.6/0-20.json";
}
