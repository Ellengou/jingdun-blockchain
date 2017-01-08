//
//  QKLUserInfo.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLUserInfo.h"
#import "QKLLanguages.h"

@implementation QKLUserInfo


- (instancetype) initWithDict:(NSDictionary *)dict {
    self = [super init];
    if (dict) {
        for (NSString *key in [dict keyEnumerator]) {
            id obj = [dict objectForKey:key];
            if ([key isEqualToString:@"userid"]) {
                _userid = [obj integerValue];
            } else if ([key isEqualToString:@"username"]) {
                _username = obj;
            } else if ([key isEqualToString:@"nickname"]) {
                _nickname = obj;
            } else if ([key isEqualToString:@"icon"]) {
                _icon = obj;
            } else if ([key isEqualToString:@"iconid"]) {
                _iconid = [obj integerValue];
            } else if ([key isEqualToString:@"createdate"]) {
                _createdate = [obj longLongValue];
            } else if ([key isEqualToString:@"registerdate"]) {
                _registerdate = [obj longLongValue];
            } else if ([key isEqualToString:@"updatedate"]) {
                _updatedate = [obj longLongValue];
            } else if ([key isEqualToString:@"payconfiglist"]) {
                NSMutableArray *arr = [NSMutableArray new];
                if ([obj isKindOfClass:[NSArray class]]) {
                    for (NSDictionary *dict in obj) {
                        [arr addObject:[[QKLPayAccountInfoNode alloc] initWithDict:dict]];
                    }
                } else if ([obj isKindOfClass:[NSDictionary class]]) {
                    [arr addObject:[[QKLPayAccountInfoNode alloc] initWithDict:obj]];
                }
                _payconfiglist = [arr copy];
            }
        }
    }
    return self;
}

- (QKLPayAccountInfoNode *)isPaypalCorrect {
    if (_payconfiglist.count) {
        for (QKLPayAccountInfoNode *node in _payconfiglist) {
            if (node.stype == PayAccountTypePaypal) {
                if (node.carnum && node.sname) {
                    return node;
                }
            }
        }
    }
    return nil;
}

- (QKLPayAccountInfoNode *)isAlipayCorrect {
    if (_payconfiglist.count) {
        for (QKLPayAccountInfoNode *node in _payconfiglist) {
            if (node.stype == PayAccountTypeAlipay) {
                if (node.carnum && node.sname) {
                    return node;
                }
            }
        }
    }
    return nil;
}

@end
