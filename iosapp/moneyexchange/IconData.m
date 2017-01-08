//
//  IconData.m
//  huimeicity
//
//  Created by dapeng on 16/5/14.
//  Copyright © 2016年 nayuntec. All rights reserved.
//

#import "IconData.h"
#import "QKLLanguages.h"

@implementation IconData

- (instancetype) initWithDict:(NSDictionary *)dict {
    self = [super init];
    if (dict) {
        for (NSString *key in [dict keyEnumerator]) {
            id obj = [dict objectForKey:key];
            if ([key isEqualToString:tag_imgid]) {
                _imgId = [obj integerValue];
            } else if ([key isEqualToString:tag_imgurl]) {
                _imgUrl = obj;
            }
        }
    }
    return self;
}

@end
