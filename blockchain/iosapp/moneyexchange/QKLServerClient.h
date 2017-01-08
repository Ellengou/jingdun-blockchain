//
//  QKLServerClient.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, QKLRequestState) {
    QKLRequestStateReload,
    QKLRequestStateAppend,
};

typedef NS_ENUM(NSInteger, QKLURLRequestType) {
    QKLURLRequestTypeGet = 1,
    QKLURLRequestTypePost = 2,
};

typedef NS_ENUM(NSInteger, NetworkStatus){
    NetworkStatusUnknown           = -1, //未知网络
    NetworkStatusNotReachable      = 0,    //没有网络
    NetworkStatusReachableViaWWAN  = 1,    //手机自带网络
    NetworkStatusReachableViaWiFi  = 2     //wifi
};

typedef NS_ENUM(NSInteger, ConvertMoneyType) {
    ConvertMoneyTypeCNY = 0,
    ConvertMoneyTypeUSD = 1,
};

typedef NS_ENUM(NSInteger, ItemListType) {
    ItemListTypeFirstPage = 0,
    ItemListTypeMineNeed  = 1,
    ItemListTypeTransferHis = 2,
};

typedef void( ^ QKLResponseSuccess)(id response);
typedef void( ^ QKLResponseFail)(NSError *error);

typedef void( ^ QKLUploadProgress)(int64_t bytesProgress,
int64_t totalBytesProgress);

typedef void( ^ QKLDownloadProgress)(int64_t bytesProgress,
int64_t totalBytesProgress);

/**
 *  方便管理请求任务。执行取消，暂停，继续等任务.
 *  - (void)cancel，取消任务
 *  - (void)suspend，暂停任务
 *  - (void)resume，继续任务
 */
typedef NSURLSessionTask QKLURLSessionTask;


@interface QKLServerClient : NSObject

/**
 *  获取单例对象
 */
+ (QKLServerClient *)sharedNetworking;

/**
 *  获取网络
 */
@property (nonatomic,assign)NetworkStatus networkStats;

/**
 *  开启网络监测
 */
+ (void)startMonitoring;

/**
 *  get请求方法,block回调
 *
 *  @param url     请求连接，根路径
 *  @param params  参数
 *  @param success 请求成功返回数据
 *  @param fail    请求失败
 *  @param showHUD 是否显示HUD
 */
-(QKLURLSessionTask *)getWithUrl:(NSString *)url
                          params:(NSDictionary *)params
                         success:(QKLResponseSuccess)success
                            fail:(QKLResponseFail)fail
                         showHUD:(UIView *)showHUD;

/**
 *  post请求方法,block回调
 *
 *  @param url     请求连接，根路径
 *  @param params  参数
 *  @param success 请求成功返回数据
 *  @param fail    请求失败
 *  @param showHUD 是否显示HUD
 */
-(QKLURLSessionTask *)postWithUrl:(NSString *)url
                           params:(NSDictionary *)params
                          success:(QKLResponseSuccess)success
                             fail:(QKLResponseFail)fail
                          showHUD:(UIView *)showHUD;

/**
 *  上传图片方法
 *
 *  @param images     上传的图片
 *  @param url        请求连接，根路径
 *  @param params     参数
 *  @param progress   上传进度
 *  @param success    请求成功返回数据
 *  @param fail       请求失败
 *  @param showHUD    是否显示HUD
 */
-(QKLURLSessionTask *)uploadWithImage:(NSArray *)images
                                  url:(NSString *)url
                               params:(NSDictionary *)params
                             progress:(QKLUploadProgress)progress
                              success:(QKLResponseSuccess)success
                                 fail:(QKLResponseFail)fail
                              showHUD:(UIView *)showHUD;

/**
 *  下载文件方法
 *
 *  @param url           下载地址
 *  @param saveToPath    文件保存的路径,如果不传则保存到Documents目录下，以文件本来的名字命名
 *  @param progressBlock 下载进度回调
 *  @param success       下载完成
 *  @param fail          失败
 *  @param showHUD       是否显示HUD
 *  @return 返回请求任务对象，便于操作
 */
- (QKLURLSessionTask *)downloadWithUrl:(NSString *)url
                            saveToPath:(NSString *)saveToPath
                              progress:(QKLDownloadProgress )progressBlock
                               success:(QKLResponseSuccess )success
                               failure:(QKLResponseFail )fail
                               showHUD:(UIView*)showHUD;


@end
