//
//  QKLConvertInfoNode.m
//  moneyexchange
//
//  Created by dapeng on 16/10/21.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLConvertInfoNode.h"

@implementation QKLConvertInfoNode

-(instancetype)initWithDict:(NSDictionary *)dict {
    self = [super init];
    if (!dict) {
        return self;
    }
    for (NSString *key in [dict keyEnumerator]) {
        id obj = [dict objectForKey:key];
        if ([key isEqualToString:@"cost"]) {
            _cost = [obj floatValue];
        } else if ([key isEqualToString:@"createdate"]) {
            _createdate = [obj longLongValue];
        } else if ([key isEqualToString:@"itemid"]) {
            _itemid = [obj integerValue];
        } else if ([key isEqualToString:@"money"]) {
            _money = [obj floatValue];
        } else if ([key isEqualToString:@"sflag"]) {
            _sflag = [obj integerValue];
        } else if ([key isEqualToString:@"sstatus"]) {
            _sstatus = [obj integerValue];
        } else if ([key isEqualToString:@"stype"]) {
            _stype = [obj integerValue];
        } else if ([key isEqualToString:@"updatedate"]) {
            _updatedate = [obj longLongValue];
        } else if ([key isEqualToString:@"userid"]) {
            _userid = [obj integerValue];
        }
    }
    return self;
}
@end
