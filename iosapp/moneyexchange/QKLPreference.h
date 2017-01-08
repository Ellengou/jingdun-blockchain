//
//  QKLPreference.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface QKLPreference : NSObject

+ (NSString *)getLoginedAccount;

+ (void)writeLoginedAccount:(NSString *)account;

@end
