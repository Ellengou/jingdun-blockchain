//
//  IconData.h
//  huimeicity
//
//  Created by dapeng on 16/5/14.
//  Copyright © 2016年 nayuntec. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface IconData : NSObject

@property (assign, nonatomic) NSInteger imgId;

@property (copy, nonatomic) NSString *imgUrl;

- (instancetype) initWithDict:(NSDictionary *)dict;

@end
