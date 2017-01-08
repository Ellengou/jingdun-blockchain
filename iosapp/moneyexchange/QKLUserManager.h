//
//  QKLUserManager.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "QKLUtils.h"
#import "QKLUserInfo.h"
#import "QKLSumOfMoneyNode.h"

@interface QKLUserManager : NSObject

+ (QKLUserManager *)shareInstanceWithVC:(UIViewController *)parentVC;
/**
 *  每次打开app的自动登录
 */
- (void)autoLogIn;
/**
 *  用户名密码登录
 *
 *  @param account  用户名 即为手机号
 *  @param password 密码
 */
- (void)loginWithAccount:(NSString *)account password:(NSString *)password;
/**
 *  登出
 */
- (void)logOut;

- (void)logOutLocal;

- (void)updateUserInfo;

- (QKLUserInfo *)getLoginUserInfo;

- (BOOL)hasLoggin;

- (NSString *)getUserToken;

- (QKLSumOfMoneyNode *)getCNYSumNode;

- (QKLSumOfMoneyNode *)getUSDSumNode;

@end
