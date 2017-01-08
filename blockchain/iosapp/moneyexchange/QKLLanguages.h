//
//  QKLLanguages.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#ifndef QKLLanguages_h
#define QKLLanguages_h
//QKL key
#define QKL_APP_KEY @"JD_QHD_IOS"
#define QKL_APP_SECRET  @"c636285eac626c1d6148bebb47560ca7"

#define QKL_DEFAULT_ITEM_COUNT  [NSNumber numberWithInteger:20]

//Third party key

//Http server path

//Request response tags
#define tag_app_key @"appkey"
#define tag_app_sig @"appsig"
#define tag_app_version @"versioncode"
#define server_api_default @"qhdapi/api"

#define tag_rc  @"rc"
#define tag_msg @"msg"
#define tag_data    @"data"
#define tag_token   @"token"
#define tag_userdata    @"userdata"
#define tag_userid  @"userid"
#define tag_username    @"username"
#define tag_icondata    @"icondata"
#define tag_imgid   @"imgid"
#define tag_imgurl  @"imgurl"
#define tag_scode   @"scode"
#define tag_password    @"password"
#define tag_refcode @"refcode"
#define tag_pagesize    @"pagesize"
#define tag_pagenum   @"pagenum"
#define tag_viewuserid  @"viewuserid"
#define tag_file    @"file"
#define tag_nickname    @"nickname"
#define tag_paypalnum   @"paypalnum"
#define tag_paypalname  @"paypalname"
#define tag_alipaynum   @"alipaynum"
#define tag_alipayname  @"alipayname"
#define tag_sflag       @"sflag"
#define tag_keyword     @"keyword"
#define tag_stype       @"stype"
#define tag_totalpage   @"totalpage"
#define tag_money   @"money"
#define tag_itemid  @"itemid"
#define tag_itemids  @"itemids"
#define tag_payorder    @"payorder"
#define tag_paycost    @"paycost"
#define tag_paystype    @"paystype"
#define tag_itemlist    @"itemlist"
#define tag_chargedata    @"chargedata"
#define tag_confirmation   @"paypalid"
#define tag_moneylist   @"moneylist"


#define method_sel  @"method"
#define method_sel_scodeget @"api.scodeget"
#define method_sel_user_reister @"api.register"
#define method_sel_user_login   @"api.login"
#define method_sel_exit @"api.exit"
#define method_sel_password_reset  @"api.passwordreset"
#define method_sel_userview @"api.userview"
#define method_sel_icon_upload    @"api.iconupload"
#define method_sel_user_perfect @"api.userperfect"
#define method_sel_itemlist @"api.itemlist"
#define method_sel_sysrecom @"api.sysrecom"
#define method_sel_itemadd  @"api.itemadd"
#define method_sel_itemdel  @"api.itemdel"
#define method_sel_orderadd @"api.orderadd"
#define method_sel_orderpay @"api.orderpay"

//Inner app description
#define lang_convert_doller_to_rmb  @"兑换可获得人民币："
#define lang_convert_rmb_to_dollor  @"兑换可获得美元："

#define lang_net_err    @"网络请求出错，请检查网络后重试"
#define lang_unknow_err @"未知错误"

#define lang_phone_number_format_err   @"请检查电话号码"
#define lang_not_get_scode  @"请先获取验证码"
#define lang_not_write_password @"请设置密码，并牢记"
#define lang_password_set_err   @"密码设置有误，长度 6-18 位，仅限大小写字母和数字"
#define lang_password_set_success   @"密码修改成功"
#define lang_not_check_password @"请再次输入密码"
#define lang_not_equal_twice    @"密码设置不一致，请重新确认"
#define lang_account_password   @"请输入账号密码"
#define lang_complete_mine_info @"点击完善个人信息"
#define lang_picker_img_err @"获取新头像失败，请重试"
#define lang_avatar_upload_succ @"头像上传成功"
#define lang_avatar_upload_failed   @"头像上传失败，请检查网络后重试"
#define lang_user_info_update_succ  @"资料更新成功"

#define lang_pick_avatar        @"设置头像"
#define lang_updating_avatar             @"头像上传中"
#define lang_updating_avatar_fail        @"头像上传失败"
#define lang_camera             @"相机"
#define lang_photos				@"图库"
#define lang_img_pick_err   @"图片选取失败!"
#define lang_img_format_err @"选取文件格式错误，请重新选取图片"

#define lang_has_not_login  @"请先登陆"

#define lang_hint       @"提示"
#define lang_cancel     @"取消"
#define lang_ok         @"确定"
#define lang_no_more    @"已无更多数据"
#define lang_no_record_data @"暂无相关数据"
#define lang_web_load_err @"加载失败，请下拉刷新重试"
#define lang_order_complete @"已完成"

#define lang_need_desc  @"需求信息"
#define lang_transfer_desc  @"转账信息"

#define lang_enter_correct_num  @"请输入需要兑换的金额，并再次确认币种"
#define lang_enter_correct_sum  @"请输入合理的金额\n最小%.2f，最大%.2f"

//inner app string format


//universial
#define SCREEN_H_480	[[UIScreen mainScreen] bounds].size.height == 480.0f

#define SCREEN_H_667	[[UIScreen mainScreen] bounds].size.height == 667.0f

#define SCREEN_H_736	[[UIScreen mainScreen] bounds].size.height == 736.0f

#define IMG_TILE_WIDTH	([[UIScreen mainScreen] bounds].size.width - 40.0f)/3.0f

#define IMG_TILE_HEIGHT (IMG_TILE_WIDTH * 9.0f / 16.0f)

#define pAccount @"account"

#endif /* QKLLanguages_h */
