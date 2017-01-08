//
//  QKLPayAccountInfoNode.m
//  moneyexchange
//
//  Created by dapeng on 16/10/22.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLPayAccountInfoNode.h"

@implementation QKLPayAccountInfoNode

-(instancetype)initWithDict:(NSDictionary *)dict {
    self = [super init];
    if (!dict) {
        return self;
    }
    for (NSString *key in [dict keyEnumerator]) {
        id obj = [dict objectForKey:key];
        if ([key isEqualToString:@"carnum"]) {
            _carnum = obj;
        } else if ([key isEqualToString:@"updatedate"]) {
            _updatedate = [obj longLongValue];
        } else if ([key isEqualToString:@"userid"]) {
            _userid = [obj integerValue];
        } else if ([key isEqualToString:@"sname"]) {
            _sname = obj;
        } else if ([key isEqualToString:@"createdate"]) {
            _createdate = [obj longLongValue];
        } else if ([key isEqualToString:@"stype"]) {
            _stype = [obj integerValue];
        } else if ([key isEqualToString:@"seq"]) {
            _seq = [obj integerValue];
        }
    }
    return self;
}

@end
