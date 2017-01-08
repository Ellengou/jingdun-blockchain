//
//  QKLUserInfo.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "QKLPayAccountInfoNode.h"

@interface QKLUserInfo : NSObject


@property (copy, nonatomic) NSString *nickname;

@property (copy, nonatomic) NSString *icon;

@property (assign, nonatomic) NSInteger iconid;

@property (copy, nonatomic) NSString *username;

@property (assign, nonatomic) NSInteger userid;

@property (assign, nonatomic) long long createdate;

@property (assign, nonatomic) long long registerdate;

@property (assign, nonatomic) long long updatedate;

@property (nonatomic, strong) NSArray *payconfiglist;

- (instancetype) initWithDict:(NSDictionary *)dict;

- (QKLPayAccountInfoNode *)isPaypalCorrect;

- (QKLPayAccountInfoNode *)isAlipayCorrect;

@end
