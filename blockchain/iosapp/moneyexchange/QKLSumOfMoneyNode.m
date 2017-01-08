//
//  QKLSumOfMoneyNode.m
//  moneyexchange
//
//  Created by dapeng on 2016/11/8.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLSumOfMoneyNode.h"

@implementation QKLSumOfMoneyNode

- (instancetype)initWithDict:(NSDictionary *)dict {
    self = [super init];
    if (dict) {
        for (NSString *key in [dict keyEnumerator]) {
            id obj = [dict objectForKey:key];
            if ([key isEqualToString:@"max"]) {
                _max = [obj floatValue];
            } else if ([key isEqualToString:@"min"]) {
                _min = [obj floatValue];
            } else if ([key isEqualToString:@"stype"]) {
                _stype = [obj integerValue];
            }
        }
    }
    return self;
}

@end
