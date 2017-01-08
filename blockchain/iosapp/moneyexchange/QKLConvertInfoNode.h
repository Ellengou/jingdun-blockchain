//
//  QKLConvertInfoNode.h
//  moneyexchange
//
//  Created by dapeng on 16/10/21.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "QKLServerClient.h"

@interface QKLConvertInfoNode : NSObject

@property (nonatomic, assign) CGFloat cost;

@property (nonatomic, assign) long long createdate;

@property (nonatomic, assign) NSInteger itemid;

@property (nonatomic, assign) CGFloat money;

@property (nonatomic, assign) ItemListType sflag;

@property (nonatomic, assign) NSInteger sstatus;

@property (nonatomic, assign) ConvertMoneyType stype;

@property (nonatomic, assign) long long updatedate;

@property (nonatomic, assign) NSInteger userid;

- (instancetype)initWithDict:(NSDictionary *)dict;

@end
