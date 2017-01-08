//
//  QKLPreference.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLPreference.h"
#import "QKLLanguages.h"

@implementation QKLPreference

+ (NSString *)getLoginedAccount {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSString *account = [userDefaults objectForKey:pAccount];
    return account;
}

+ (void)writeLoginedAccount:(NSString *)account {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setValue:account forKey:pAccount];
    [userDefaults synchronize];
}

@end
