//
//  AppDelegate.m
//  moneyexchange
//
//  Created by dapeng on 16/10/14.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "AppDelegate.h"
#import "MainViewController.h"
#import "Pingpp.h"
#import "PayPalMobile.h"

@implementation NavRootViewController

-(id)initWithRootViewController:(UIViewController *)rootViewController {
    NavRootViewController* nvc = [super initWithRootViewController:rootViewController];
    self.interactivePopGestureRecognizer.delegate = self;
    nvc.delegate = self;
    return nvc;
}

-(void)navigationController:(UINavigationController *)navigationController willShowViewController:(UIViewController *)viewController animated:(BOOL)animated{
    
}

-(void)navigationController:(UINavigationController *)navigationController didShowViewController:(UIViewController *)viewController animated:(BOOL)animated {
    if (navigationController.viewControllers.count == 1)
        self.currentShowVC = nil;
    else
        self.currentShowVC = viewController;
}

-(BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer{
    
    if (gestureRecognizer == self.interactivePopGestureRecognizer) {
        return (self.currentShowVC == self.topViewController);
    }
    return YES;
}

- (UIInterfaceOrientationMask)supportedInterfaceOrientations {
    return UIInterfaceOrientationMaskPortrait;
}

- (BOOL)shouldAutorotate {
    return NO;
}

@end

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    CGRect winFrame = [UIScreen mainScreen].bounds;
    
    self.window = [[UIWindow alloc] initWithFrame:winFrame];
    
    self.rootVC = [[MainViewController alloc] initWithNibName:@"MainViewController" bundle:nil];
    
    self.rootNavi = [[NavRootViewController alloc] initWithRootViewController:_rootVC];
    [_rootNavi setNavigationBarHidden:YES];
    [self.window setRootViewController:_rootNavi];
    [self.window makeKeyAndVisible];
    
    //1.初始化paypal
    [PayPalMobile initializeWithClientIdsForEnvironments:@{PayPalEnvironmentProduction : @"AbX_1hNVSAX8FMM-pEA_BnA2BQFaarOYZu0fRhTZZBOPQQKBbEJRVUTmA4GkVgtfEIFxUuBS-jJqTUbS", PayPalEnvironmentSandbox : @"AYAo1SDqerLUXdmTGTP67sx6JpKwg7gf-y5Trr9qmjekRUX8I-tjJbeAxwMOilcYkF1o4Q6hnCAcUE9o"}];
    
    return YES;
}

//适用于iOS8及以下
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(nullable NSString *)sourceApplication annotation:(id)annotation {
    //调用ping++的handleOpenURL,withCompletion:nil时处理结果在createPayment中处理
//    [Pingpp handleOpenURL:url withCompletion:nil];
    return NO;
}
//使用于iOS9及以上
- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey, id> *)options {
    //调用ping++的handleOpenURL
    [Pingpp handleOpenURL:url withCompletion:nil];
    return NO;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}


- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}


@end
