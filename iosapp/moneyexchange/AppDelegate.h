//
//  AppDelegate.h
//  moneyexchange
//
//  Created by dapeng on 16/10/14.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NavRootViewController : UINavigationController <UIGestureRecognizerDelegate,UINavigationControllerDelegate>
@property(nonatomic,weak) UIViewController* currentShowVC;

@end

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) UIViewController *rootVC;

@property (strong, nonatomic) NavRootViewController *rootNavi;

@end

