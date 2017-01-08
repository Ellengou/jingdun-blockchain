//
//  QKLUtils.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UIView+Toast.h"
#import "QKLLanguages.h"
#import "QKLUserManager.h"
#import <UIKit/UIKit.h>
#import <YYKit.h>

@interface QKLUtils : NSObject

+ (NSInteger)getResponseCode:(NSDictionary *)response;

+ (NSString *)getResponseMsg:(NSDictionary *)response;

+ (UIView *)getTableFooterForLoadingWithWidth:(CGFloat)width;

+ (UIView *)getNoDataTableFooterWithHeight:(CGFloat)height msg:(NSString *)msg bgColor:(UIColor *)bgColor;

+ (UIImage *)cacheImageNamed:(NSString *)name;

+ (NSString *)getUpdateTimeByTime:(long long)lTime isInSecond:(BOOL)isInSecond;

+ (float)getAdjustHeightWithBase_H:(float)base_H;

+(NSString *)strUTF8Encoding:(NSString *)str;

+ (BOOL)isPureNumber:(NSString*)string;

+ (NSDictionary *)completeUserToken:(NSDictionary *)dict;

+ (void)jumpToLoginFromViewController:(UIViewController *)vc;

+ (BOOL)checkLoginStatusForViewController:(UIViewController *)vc;

+ (NSAttributedString *)getNoDataMsg:(NSString *)msg;

+ (BOOL) isAllDigits:(NSString *)str;

+ (BOOL) isAlphaNumeric:(NSString *)str;
@end
