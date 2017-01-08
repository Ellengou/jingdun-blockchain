//
//  QKLServerClient.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLServerClient.h"
#import "QKLUtils.h"
#import "QKLLanguages.h"
#import <AFNetworking.h>
#import <MBProgressHUD.h>
#import <AFNetworkActivityIndicatorManager.h>

static NSMutableArray *tasks;

static AFHTTPSessionManager *serverClient;

@implementation QKLServerClient

#pragma makr - 开始监听网络连接

+ (void)startMonitoring
{
    // 1.获得网络监控的管理者
    AFNetworkReachabilityManager *mgr = [AFNetworkReachabilityManager sharedManager];
    // 2.设置网络状态改变后的处理
    [mgr setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
        // 当网络状态改变了, 就会调用这个block
        switch (status)
        {
            case AFNetworkReachabilityStatusUnknown: // 未知网络
                [QKLServerClient sharedNetworking].networkStats = NetworkStatusUnknown;
                break;
            case AFNetworkReachabilityStatusNotReachable: // 没有网络(断网)
                [QKLServerClient sharedNetworking].networkStats = NetworkStatusNotReachable;
                break;
            case AFNetworkReachabilityStatusReachableViaWWAN: // 手机自带网络
                [QKLServerClient sharedNetworking].networkStats = NetworkStatusReachableViaWWAN;
                break;
            case AFNetworkReachabilityStatusReachableViaWiFi: // WIFI
                [QKLServerClient sharedNetworking].networkStats = NetworkStatusReachableViaWiFi;
                break;
        }
    }];
    [mgr startMonitoring];
}

+ (QKLServerClient *)sharedNetworking
{
    static QKLServerClient *handler = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        handler = [[QKLServerClient alloc] init];
        [handler initAFManager];
    });
    return handler;
}

-(NSMutableArray *)tasks{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        tasks = [[NSMutableArray alloc] init];
    });
    return tasks;
}
//http://www.huimeic.com/myr.api/api?appsig=5dcd90429e991ec5e609ed172ee82bc3&appkey=test&method=api.itemlist&orderby=1&pagenum=1&stype=0
-(void)initAFManager{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        [AFNetworkActivityIndicatorManager sharedManager].enabled = YES;
        serverClient = [[AFHTTPSessionManager alloc] initWithBaseURL:[NSURL URLWithString:@"http://121.40.79.113:80/"]];

        serverClient.responseSerializer = [AFJSONResponseSerializer serializer];//设置返回数据为json
        serverClient.requestSerializer.stringEncoding = NSUTF8StringEncoding;
        serverClient.requestSerializer.timeoutInterval = 20;
        serverClient.responseSerializer.acceptableContentTypes = [NSSet setWithArray:@[@"application/json",
                                                                                       @"text/html",
                                                                                       @"text/json",
                                                                                       @"text/plain",
                                                                                       @"text/javascript",
                                                                                       @"text/xml",
                                                                                       @"image/*"]];
    });
}

//API
- (NSDictionary *)completeParams:(NSDictionary *)params {
    
    NSMutableDictionary *dict = [@{tag_app_key : QKL_APP_KEY} mutableCopy];
    /*
     NSString * token = [[QKLUserManager shareInstance] getToken];
     if (token && token.length > 0) {
     [dict setObject:token forKey:tag_cstoken];
     }
     
     NSString *identifier = [self getIdentifier];
     if (identifier && identifier.length > 0) {
     [dict setObject:identifier forKey:tag_identifier];
     }
     
     NSString *apkSource = [NSString stringWithFormat:@"%ld",(long)[CSUtils getApkSource]];
     if (apkSource && apkSource.length > 0) {
     [dict setObject:apkSource forKey:tag_appsource];
     }*/
     
     NSString *apkVersion = [self getApkVersion];
     if (apkVersion && apkVersion.length > 0) {
         [dict setObject:apkVersion forKey:tag_app_version];
     }
    
    
    if (params) {
        [dict addEntriesFromDictionary:params];
    }
    
    NSString *appSig = [self getAppSigByParamDict:dict secretKey:QKL_APP_SECRET];
    
    [dict setObject:appSig forKey: tag_app_sig];
    
    return dict;
}

- (NSString *)getIdentifier {
    /*
     NSString *identifier = [[Preference getInstance] readUDID];
     if(identifier && identifier.length > 0){
     return identifier;
     } else {
     identifier = [CSUtils getUDIDFromKeyChain];
     if (identifier == nil || [identifier isEqualToString:@""]) {
     identifier = [CSUtils writeUDIDToKeyChain];
     }
     }
     */
    return @"";//identifier;
}

- (NSString *)getApkVersion {
    NSString *build = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleVersion"];
    
    return [NSString stringWithFormat:@"%@", build];
}

- (NSString *)getAppSigByParamDict:(NSDictionary *)paramDict secretKey:(NSString *)strKey
{
    
    NSString *appSecret = QKL_APP_SECRET;
    if (nil != strKey) {
        appSecret = strKey;
    }
    
    NSArray *sortedKeys = [[paramDict allKeys] sortedArrayUsingSelector:@selector(compare:)];
    NSString *sortedParam = [[NSString alloc] init];
    for (NSString *key in sortedKeys) {
        id value = [paramDict objectForKey:key];
        if ([value isKindOfClass:[NSString class]] && ![key isEqualToString:@"inputFile"]) {
            sortedParam = [NSString stringWithFormat:@"%@&%@=%@", sortedParam, key, [paramDict objectForKey:key]];
        }
    }
    
    sortedParam = [NSString stringWithFormat:@"%@%@", appSecret, sortedParam];
    
    return [sortedParam md5String];
}

-(QKLURLSessionTask *)getWithUrl:(NSString *)url
                          params:(NSDictionary *)params
                         success:(QKLResponseSuccess)success
                            fail:(QKLResponseFail)fail
                         showHUD:(UIView *)showHUD{
    
    return [self baseRequestType:QKLURLRequestTypeGet url:url params:params success:success fail:fail showHUD:showHUD];
    
}

-(QKLURLSessionTask *)postWithUrl:(NSString *)url
                           params:(NSDictionary *)params
                          success:(QKLResponseSuccess)success
                             fail:(QKLResponseFail)fail
                          showHUD:(UIView *)showHUD{
    return [self baseRequestType:QKLURLRequestTypePost url:url params:params success:success fail:fail showHUD:showHUD];
}

-(QKLURLSessionTask *)baseRequestType:(NSUInteger)type
                                  url:(NSString *)url
                               params:(NSDictionary *)params
                              success:(QKLResponseSuccess)success
                                 fail:(QKLResponseFail)fail
                              showHUD:(UIView *)showHUD{
    if (url==nil) {
        return nil;
    }
    
    if (showHUD) {
        [MBProgressHUD showHUDAddedTo:showHUD animated:YES];
    }
    
    NSString *urlStr=[NSURL URLWithString:url]?url:[QKLUtils strUTF8Encoding:url];
    NSDictionary *dictParams = [self completeParams:params];
    QKLURLSessionTask *sessionTask=nil;
    
    if (type==QKLURLRequestTypeGet) {
        sessionTask = [serverClient GET:urlStr parameters:dictParams progress:^(NSProgress * _Nonnull downloadProgress) {
            
        } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
            if (success) {
                success(responseObject);
            }
            
            [[self tasks] removeObject:sessionTask];
            
            if (showHUD) {
                [MBProgressHUD hideHUDForView:showHUD animated:YES];
            }
            
        } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
            if (fail) {
                fail(error);
            }
            
            [[self tasks] removeObject:sessionTask];
            
            if (showHUD) {
                [MBProgressHUD hideHUDForView:showHUD animated:YES];
            }
            
        }];
        
    }else{
        sessionTask = [serverClient POST:url parameters:dictParams progress:^(NSProgress * _Nonnull uploadProgress) {
            
        } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
            if (success) {
                success(responseObject);
            }
            
            [[self tasks] removeObject:sessionTask];
            
            if (showHUD) {
                [MBProgressHUD hideHUDForView:showHUD animated:YES];
            }
            
        } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
            if (fail) {
                fail(error);
            }
            
            [[self tasks] removeObject:sessionTask];
            
            if (showHUD) {
                [MBProgressHUD hideHUDForView:showHUD animated:YES];
            }
            
        }];
        
        
    }
    
    if (sessionTask) {
        [[self tasks] addObject:sessionTask];
    }
    
    return sessionTask;
    
}

-(QKLURLSessionTask *)uploadWithImage:(NSArray *)images
                                  url:(NSString *)url
                               params:(NSDictionary *)params
                             progress:(QKLUploadProgress)progress
                              success:(QKLResponseSuccess)success
                                 fail:(QKLResponseFail)fail
                              showHUD:(UIView *)showHUD {
    if (url==nil) {
        return nil;
    }
    
    if (showHUD) {
        [MBProgressHUD showHUDAddedTo:showHUD animated:YES];
    }
    
    //检查地址中是否有中文
    NSString *urlStr=[NSURL URLWithString:url]?url:[QKLUtils strUTF8Encoding:url];
    
    QKLURLSessionTask *sessionTask = [serverClient POST:urlStr parameters:params constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        //压缩图片
        for (UIImage *image in images) {
            NSData *imageData = UIImageJPEGRepresentation(image, 1.0);
            
            NSString *imageFileName;
            NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
            formatter.dateFormat = @"yyyyMMddHHmmss";
            NSString *str = [formatter stringFromDate:[NSDate date]];
            imageFileName = [NSString stringWithFormat:@"%@.jpg", str];
            // 上传图片，以文件流的格式
            [formData appendPartWithFileData:imageData name:@"file" fileName:imageFileName mimeType:@"image/jpeg"];
        }
        
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        if (progress) {
            progress(uploadProgress.completedUnitCount, uploadProgress.totalUnitCount);
        }
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        if (success) {
            success(responseObject);
        }
        
        [[self tasks] removeObject:sessionTask];
        
        if (showHUD) {
            [MBProgressHUD hideHUDForView:showHUD animated:YES];
        }
        
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        if (fail) {
            fail(error);
        }
        
        [[self tasks] removeObject:sessionTask];
        
        if (showHUD) {
            [MBProgressHUD hideHUDForView:showHUD animated:YES];
        }
        
    }];
    
    
    if (sessionTask) {
        [[self tasks] addObject:sessionTask];
    }
    
    return sessionTask;
}

- (QKLURLSessionTask *)downloadWithUrl:(NSString *)url
                            saveToPath:(NSString *)saveToPath
                              progress:(QKLDownloadProgress)progressBlock
                               success:(QKLResponseSuccess)success
                               failure:(QKLResponseFail)fail
                               showHUD:(UIView *)showHUD{
    
    
    if (url==nil) {
        return nil;
    }
    
    if (showHUD) {
        [MBProgressHUD showHUDAddedTo:showHUD animated:YES];
    }
    
    NSURLRequest *downloadRequest = [NSURLRequest requestWithURL:[NSURL URLWithString:url]];
    QKLURLSessionTask *sessionTask = nil;
    
    sessionTask = [serverClient downloadTaskWithRequest:downloadRequest progress:^(NSProgress * _Nonnull downloadProgress) {
        //回到主线程刷新UI
        dispatch_async(dispatch_get_main_queue(), ^{
            if (progressBlock) {
                progressBlock(downloadProgress.completedUnitCount, downloadProgress.totalUnitCount);
            }
        });
        
    } destination:^NSURL * _Nonnull(NSURL * _Nonnull targetPath, NSURLResponse * _Nonnull response) {
        if (!saveToPath) {
            
            NSURL *downloadURL = [[NSFileManager defaultManager] URLForDirectory:NSDocumentDirectory inDomain:NSUserDomainMask appropriateForURL:nil create:NO error:nil];
            return [downloadURL URLByAppendingPathComponent:[response suggestedFilename]];
            
        }else{
            return [NSURL fileURLWithPath:saveToPath];
            
        }
        
    } completionHandler:^(NSURLResponse * _Nonnull response, NSURL * _Nullable filePath, NSError * _Nullable error) {
        
        [[self tasks] removeObject:sessionTask];
        
        if (error == nil) {
            if (success) {
                success([filePath path]);//返回完整路径
            }
            
        } else {
            if (fail) {
                fail(error);
            }
        }
        
        if (showHUD) {
            [MBProgressHUD hideHUDForView:showHUD animated:YES];
        }
        
    }];
    
    //开始启动任务
    [sessionTask resume];
    if (sessionTask) {
        [[self tasks] addObject:sessionTask];
    }
    
    return sessionTask;
}


@end
