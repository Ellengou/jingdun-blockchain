//
//  QKLUserManager.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLUserManager.h"
#import "QKLServerClient.h"
#import "QKLPreference.h"
#import <SSKeychain.h>

NSString *const QKLKeyChainServiceName = @"com.yuyouk.moneyexchange.keychain.service";
NSString *const QKLUserToken = @"userToken";

NSString *const QKLUserHasLogInNotification = @"userHasLogInSucc";
NSString *const QKLUserHasLogOutNotification = @"userHasLogOutSucc";

@interface QKLUserManager ()
@property (weak, nonatomic) UIViewController *currentVC;

@property (copy, nonatomic) NSString *loginAccount;

@property (copy, nonatomic) NSString *loginPassword;

@property (copy, nonatomic) NSString *userToken;

@property (strong, nonatomic) QKLUserInfo *userInfo;

@property (strong, nonatomic) QKLSumOfMoneyNode *CNYSum;

@property (strong, nonatomic) QKLSumOfMoneyNode *USDSum;

@end

@implementation QKLUserManager

+ (QKLUserManager *)shareInstanceWithVC:(UIViewController *)parentVC {
    static QKLUserManager *manager;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        manager = [QKLUserManager new];
    });
    if (parentVC) {
        manager.currentVC = parentVC;
    }
    return manager;
}

- (void)autoLogIn {//auto login 使用本地存储的account 和 password进行
    [self readPasswordInfoFromKeychain];
    [self requestLogin];
}

- (void)loginWithAccount:(NSString *)account password:(NSString *)password {
    _loginAccount = account;
    _loginPassword = password;
    [self savePasswordIntoKeychain];
    [self requestLogin];
}

- (void)requestLogin {
   if (_loginAccount && _loginPassword) {
        [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_user_login, tag_username: _loginAccount, tag_password: [_loginPassword md5String]}] success:^(id response) {
            if ([QKLUtils getResponseCode:response] == 0) {
                NSDictionary *data = [response objectForKey:tag_data];
                if (data) {
                    _userToken = [data objectForKey:tag_token];
                    _userInfo = [[QKLUserInfo alloc] initWithDict:[data objectForKey:tag_userdata]];
                    NSArray *sumArr = [data objectForKey:tag_moneylist];
                    if (sumArr.count) {
                        for (NSDictionary *dict in sumArr) {
                            QKLSumOfMoneyNode *node = [[QKLSumOfMoneyNode alloc] initWithDict:dict];
                            if (node.stype == ConvertMoneyTypeCNY) {
                                _CNYSum = node;
                            } else if (node.stype == ConvertMoneyTypeUSD) {
                                _USDSum = node;
                            }
                        }
                    }
                }
                [[NSNotificationCenter defaultCenter] postNotificationName:QKLUserHasLogInNotification object:nil];
            } else if(_currentVC) {
                [_currentVC.view makeToast:[QKLUtils getResponseMsg:response]];
            }
        } fail:^(NSError *error) {
            if(_currentVC) {
                [_currentVC.view makeToast:lang_net_err];
            }
        } showHUD:(_currentVC ? _currentVC.view : nil)];
    }
}

- (void)logOut {
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel:method_sel_exit}] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            [self logOutLocal];
            [[NSNotificationCenter defaultCenter] postNotificationName:QKLUserHasLogOutNotification object:nil];
        } else if(_currentVC) {
            [_currentVC.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        if(_currentVC) {
            [_currentVC.view makeToast:lang_net_err];
        }
    } showHUD:_currentVC ? _currentVC.view : nil];
}

- (void)logOutLocal {
    _userInfo = nil;
    _userToken = nil;
    [self deletePasswordInfoFromKeychain];
}

- (void)updateUserInfo {
    if (![self hasLoggin]) {
        return;
    }
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_userview, tag_viewuserid:@(_userInfo.userid), tag_stype :@(0)}]success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            NSDictionary *data = [response objectForKey:tag_data];
            if (data) {
                _userInfo = [[QKLUserInfo alloc] initWithDict:[data objectForKey:tag_userdata]];
                [[NSNotificationCenter defaultCenter] postNotificationName:QKLUserHasLogInNotification object:nil];
            }
        }
    } fail:^(NSError *error) {
        //
    } showHUD:nil];
}

- (QKLUserInfo *)getLoginUserInfo {
    return _userInfo;
}

- (BOOL)hasLoggin {
    return (_userToken != nil) && (_userInfo != nil);
}

- (NSString *)getUserToken {
    return _userToken;
}

- (QKLSumOfMoneyNode *)getCNYSumNode {
    return _CNYSum;
}

- (QKLSumOfMoneyNode *)getUSDSumNode {
    return _USDSum;
}

#pragma mark keychain handler
- (void) savePasswordIntoKeychain{
    [QKLPreference writeLoginedAccount:_loginAccount];
    [SSKeychain setPassword:_loginPassword forService:QKLKeyChainServiceName account:_loginAccount];
}

- (void) readPasswordInfoFromKeychain {
    _loginAccount = [QKLPreference getLoginedAccount];
    if (_loginAccount) {
        _loginPassword =[SSKeychain passwordForService:QKLKeyChainServiceName account:_loginAccount];
    }
}

- (void) deletePasswordInfoFromKeychain {
    [SSKeychain deletePasswordForService:QKLKeyChainServiceName account:[QKLPreference getLoginedAccount]];
    [QKLPreference writeLoginedAccount:@""];
    _loginAccount = nil;
    _loginPassword = nil;
}

@end
