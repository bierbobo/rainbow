package com.bierbobo.rainbow.util;

/**
 * api调用方，调用来源
 *
 * @author youfengkai@360buy.com
 *
 */
public enum ConsumerSources {

    /**
     * 自营
     */
    SELF(1),
    /**
     * POP
     */
    POP(2),
    /**
     * EDI
     */
    EDI(3),
    /**
     * BOOK
     */
    BOOK(4),
    /**
     * VC
     */
    VC(5),
    /**
     * EPT
     */
    EPT(6),
    /**
     * APP Store
     */
    APPSTORE(7),
    /**
     * Gigit music
     */
    DIGITMUSIC(8),
    /**
     * OFC
     */
    OFC(9),
    /**
     * Guan Gou
     */
    TUAN(10),
    /**
     * VC BOOK
     */
    VCBOOK(11),
    /**
     * WMS
     */
    WMS(12),
    /**
     * 电子书
     */
    EBOOK(13),
    /**
     * 下发服务
     */
    DELIVER(15),

    /**
     * POP book
     */
    POP_BOOK(16),

    /**
     * 获取基本信息刷新Redis
     */
    DELIVER_GETBASEINFO(17),

    /**
     * 配合下发网站图书获取
     */
    BOOK_WEB_ALL(18),

    /**
     * 获取商品名称
     */
    BOOK_WEB_NAME(19),

    /**
     * 社区获取PopPid
     */
    CLUB_GET_POPPID(20),

    /**
     * 市场组
     */
    MARKET(21),

    /**
     * 资金风险控制
     */
    RISK(22),

    /**
     * 商品详情模版 系统
     */
    TEMPLATE(23),
    /**
     * 厂商直送
     */
    DROPSHIP(24),

    /**
     * 库房归属（门店）
     */
    STOREO2O(25),
    /**
     * 搜索组
     */
    SEARCH(26),
    /**
     * 商品详情装饰系统
     */
    WDIS_DECORATION(27),
    /**
     * 给连志数据补齐使用
     */
    MAIN_DATA(28),
    /**
     * 库存组
     */
    STOCK(29),
    /**
     * 大客户
     */
    BIG_CLIENT(30),

    /**
     * 交易
     */
    TRADE(31),

    /**
     * EDI_VC业务分支
     */
    EDI_VC(32),
    /**
     * 自营总代
     */
    SELF_ZONGDAI(33),
    /**
     * 网站单品页只读
     */
    WEB_ITEMPAGE_READ(34),

    /**
     * 自营店铺化 , POP SHOP端修改店铺分类使用
     */
    SELF_SHOP(35),

    /**
     * 闪购批量上下柜
     */
    FLASH_PURCHASE(36),

    /**
     * 闪购标识
     */
    FLASH_MARK(37),
    /**
     * VCS
     */
    VCS(38),

    /**
     * 给自营后台提供的专门做商品下柜的来源，项目名：正阳门
     */
    SELF_ZHENGYANGMEN(39);

    private int value;

    private ConsumerSources(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static void main(String[] args) {
        System.out.println(ConsumerSources.STOREO2O.value);
    }
}
