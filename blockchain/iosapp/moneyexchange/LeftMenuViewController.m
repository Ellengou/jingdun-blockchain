//
//  LeftMenuViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/14.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "LeftMenuViewController.h"
#import "MineNeedListViewController.h"
#import "MineConvertHisListViewController.h"
#import "QKLUserManager.h"
#import "QKLUtils.h"
#import <SDWebImage/UIImageView+WebCache.h>

@interface LeftMenuViewController ()

@end

@implementation LeftMenuViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [[QKLUserManager shareInstanceWithVC:nil] autoLogIn];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userHasLogin:) name:QKLUserHasLogInNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userHasLogout:) name:QKLUserHasLogOutNotification object:nil];
    [self initView];
}


- (void)initView {
    if ([[QKLUserManager shareInstanceWithVC:_parentVC] hasLoggin]) {
        QKLUserInfo *userInfo = [[QKLUserManager shareInstanceWithVC:self] getLoginUserInfo];
        _labelNickName.text = userInfo.nickname;
        if (userInfo.icon.length) {
            [_ivUserAvatar sd_setImageWithURL:[NSURL URLWithString:userInfo.icon] placeholderImage:[QKLUtils cacheImageNamed:@"img_default_head"] completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL) {
                if (error || !image) {
                    return;
                }
                if (cacheType == SDImageCacheTypeNone) {
                    [UIView transitionWithView:_ivUserAvatar
                                      duration:.5f
                                       options:UIViewAnimationOptionTransitionCrossDissolve
                                    animations:^{
                                        [_ivUserAvatar setImage:image];
                                    } completion:NULL];
                } else {
                    [_ivUserAvatar setImage:image];
                }
            }];
        }
    } else {
        _labelNickName.text = @"登录";
        _ivUserAvatar.image = [QKLUtils cacheImageNamed:@"img_default_head"];
    }
}

#pragma mark notificationn handler
- (void)userHasLogin:(NSNotification *)noti {
    [self initView];
}

- (void)userHasLogout:(NSNotification *)noti {
    [self initView];
}

- (IBAction)clickMenuActionBySender:(id)sender {
    UIButton *btn = sender;
    UIViewController *vc;
    switch (btn.tag) {
        case MenuActionTypeUserLogin:
        {
            if (![[QKLUserManager shareInstanceWithVC:_parentVC] hasLoggin]) {
                vc = [[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil];
            }
        }
            break;
        case MenuActionTypeCompleteUserInfo:
        {
            if (![[QKLUserManager shareInstanceWithVC:_parentVC] hasLoggin]) {
                vc = [[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil];
            } else {
                vc = [[QKLCompleteInfoViewController alloc] initWithNibName:@"QKLCompleteInfoViewController" bundle:nil];
            }
        }
            break;
        case MenuActionTypePlatformFP:
            [_parentVC clickShowHideMenu:_parentVC.btnMenu];
            break;
        case MenuActionTypeMineNeed:
        {
            if (![[QKLUserManager shareInstanceWithVC:_parentVC] hasLoggin]) {
                vc = [[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil];
            } else {
                vc = [[MineNeedListViewController alloc] initWithNibName:@"MineNeedListViewController" bundle:nil];
            }
        }
            break;
        case MenuActionTypeConvertHis:
        {
            if (![[QKLUserManager shareInstanceWithVC:_parentVC] hasLoggin]) {
                vc = [[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil];
            } else {
                 vc = [[MineConvertHisListViewController alloc] initWithNibName:@"MineConvertHisListViewController" bundle:nil];
            }
        }
            break;
        case MenuActionTypeUserQuit:
            [[QKLUserManager shareInstanceWithVC:_parentVC] logOut];
            break;
            
        default:
            break;
    }
    if (vc) {
        [_parentVC presentViewController:vc animated:YES completion:^{
            [_parentVC clickShowHideMenu:nil];
        }];
    }
}
@end
