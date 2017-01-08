//
//  QKLPayAccountInfoNode.h
//  moneyexchange
//
//  Created by dapeng on 16/10/22.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, PayAccountType) {
    PayAccountTypeAlipay = 0,
    PayAccountTypePaypal = 1,
};

@interface QKLPayAccountInfoNode : NSObject

@property (nonatomic, strong) NSString *carnum;

@property (nonatomic, assign) long long createdate;

@property (nonatomic, assign) NSInteger seq;

@property (nonatomic, strong) NSString *sname;

@property (nonatomic, assign) PayAccountType stype;

@property (nonatomic, assign) long long updatedate;

@property (nonatomic, assign) NSInteger userid;

-(instancetype)initWithDict:(NSDictionary *)dict;

@end
