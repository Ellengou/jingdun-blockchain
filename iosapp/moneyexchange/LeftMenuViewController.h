//
//  LeftMenuViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/14.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"
#import "MainViewController.h"
#import "QKLUserLoginViewController.h"
#import "QKLCompleteInfoViewController.h"

typedef NS_ENUM(NSInteger, MenuActionType) {
    MenuActionTypeUserLogin = 0,
    MenuActionTypeCompleteUserInfo = 1,
    MenuActionTypePlatformFP = 2,
    MenuActionTypeMineNeed = 3,
    MenuActionTypeConvertHis = 4,
    MenuActionTypeUserQuit = 5
};

extern NSString *const QKLUserHasLogInNotification;

extern NSString *const QKLUserHasLogOutNotification;


@interface LeftMenuViewController : QKLViewController

@property (nonatomic, strong) MainViewController *parentVC;

@property (weak, nonatomic) IBOutlet UIImageView *ivUserAvatar;

@property (weak, nonatomic) IBOutlet UILabel *labelNickName;

- (IBAction)clickMenuActionBySender:(id)sender;

@end
