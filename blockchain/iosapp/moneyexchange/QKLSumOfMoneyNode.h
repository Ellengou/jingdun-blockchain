//
//  QKLSumOfMoneyNode.h
//  moneyexchange
//
//  Created by dapeng on 2016/11/8.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "QKLServerClient.h"


@interface QKLSumOfMoneyNode : NSObject

@property (nonatomic, assign) CGFloat max;

@property (nonatomic, assign) CGFloat min;

@property (nonatomic, assign) ConvertMoneyType stype;

- (instancetype)initWithDict:(NSDictionary *)dict;

@end
