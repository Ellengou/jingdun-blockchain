//
//  QKLCompleteInfoViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLCompleteInfoViewController.h"
#import "QKLImagePickerViewController.h"
#import "QKLServerClient.h"
#import "QKLUserManager.h"
#import "QKLUtils.h"
#import <UIImageView+WebCache.h>

@interface QKLCompleteInfoViewController ()<UITextFieldDelegate>

@end

@implementation QKLCompleteInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyBoard)]];
    [self initView];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userInfoUpdate:) name:QKLUserHasLogInNotification object:nil];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:QKLUserHasLogInNotification object:nil];
}

#pragma mark func
- (void)initView {
    QKLUserInfo *userInfo = [[QKLUserManager shareInstanceWithVC:self] getLoginUserInfo];
    _tfNickName.text = userInfo.nickname;
    if (userInfo.icon.length) {
        [_ivAvatar sd_setImageWithURL:[NSURL URLWithString:userInfo.icon] placeholderImage:[QKLUtils cacheImageNamed:@"img_default_head"] completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL) {
            if (error || !image) {
                return;
            }
            if (cacheType == SDImageCacheTypeNone) {
                [UIView transitionWithView:_ivAvatar
                                  duration:.5f
                                   options:UIViewAnimationOptionTransitionCrossDissolve
                                animations:^{
                                    [_ivAvatar setImage:image];
                                } completion:NULL];
            } else {
                [_ivAvatar setImage:image];
            }
        }];
    }
    if (userInfo.payconfiglist.count) {
        for (QKLPayAccountInfoNode *node in userInfo.payconfiglist) {
            if (node.stype == PayAccountTypeAlipay) {
                _tfAlipayAccount.text = node.carnum;
                _tfAlipayName.text = node.sname;
            } else if (node.stype == PayAccountTypePaypal) {
                _tfPaypalAccount.text = node.carnum;
                _tfPaypalName.text = node.sname;
            }
        }
    }
}

- (void)hideKeyBoard {
    [self.view endEditing:YES];
}

#pragma mark click handler
- (IBAction)clickChangeAvatar:(id)sender {
    [self hideKeyBoard];
    [[[QKLImagePickerViewController alloc] init] addPickertoParentVC:self successBlock:^(NSData *imgData){
        UIImage *img = [UIImage imageWithData:imgData];
        
        [[QKLServerClient sharedNetworking] uploadWithImage:@[img] url:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_icon_upload}] progress:^(int64_t bytesProgress, int64_t totalBytesProgress) {
            //
        } success:^(id response) {
            if ([QKLUtils getResponseCode:response] == 0) {
                [self.view makeToast:lang_avatar_upload_succ];
                [[QKLUserManager shareInstanceWithVC:nil] updateUserInfo];
                [UIView transitionWithView:_ivAvatar
                                  duration:.5f
                                   options:UIViewAnimationOptionTransitionCrossDissolve
                                animations:^{
                                    [_ivAvatar setImage:img];
                                } completion:NULL];
            } else {
                [self.view makeToast:[QKLUtils getResponseMsg:response]];
            }
        } fail:^(NSError *error) {
            [self.view makeToast:lang_avatar_upload_failed];
        } showHUD:self.view];
        
    } failedBlock:^(NSString *errMsg) {
        [self.view makeToast:lang_picker_img_err];
    }];
}

- (IBAction)clickSubmit:(id)sender {
    NSMutableDictionary *params = [NSMutableDictionary new];
    [params setObject:method_sel_user_perfect forKey:method_sel];
    
    if (_tfNickName.text.length) {
        [params setObject:_tfNickName.text forKey:tag_nickname];
    }
    if (_tfPaypalAccount.text.length) {
        [params setObject:_tfPaypalAccount.text forKey:tag_paypalnum];
    }
    if (_tfPaypalName.text.length) {
        [params setObject:_tfPaypalName.text forKey:tag_paypalname];
    }
    if (_tfAlipayAccount.text.length) {
        [params setObject:_tfAlipayAccount.text forKey:tag_alipaynum];
    }
    
    if (_tfAlipayName.text.length) {
        [params setObject:_tfAlipayName.text forKey:tag_alipayname];
    }
    
    [[QKLServerClient sharedNetworking] postWithUrl:server_api_default params:[QKLUtils completeUserToken:[params copy]] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            [self.view makeToast:lang_user_info_update_succ];
            [[QKLUserManager shareInstanceWithVC:nil] updateUserInfo];
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        [self.view makeToast:lang_net_err];
    } showHUD:self.view];
}

- (IBAction)clickBack:(id)sender {
    [self hideKeyBoard];
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self hideKeyBoard];
    return YES;
}

#pragma mark KeyBoard Notification
- (void)keyboardWillShow:(NSNotification*)aNotification {
    
    NSDictionary *userInfo = aNotification.userInfo;
    
    CGRect kbEndRect = [[userInfo objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue];
    UIViewAnimationCurve animCurve = [[userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey] intValue];
    double duration = [[userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey] doubleValue];
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (window) {
        kbEndRect = [window convertRect:kbEndRect toView:self.view];
    }
    
    [UIView beginAnimations:@"ScrollContraint" context:nil];
    [UIView setAnimationDuration:duration];
    [UIView setAnimationCurve:animCurve];
    [UIView setAnimationBeginsFromCurrentState:YES];
    
    self.constraintScrollBottom.constant = kScreenHeight - kbEndRect.origin.y;;
    [self.view layoutIfNeeded];
    
    [UIView commitAnimations];
}

- (void)keyboardWillHide:(NSNotification*)aNotification {
    
    NSDictionary *userInfo = aNotification.userInfo;
    
    CGRect kbEndRect = [[userInfo objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue];
    UIViewAnimationCurve animCurve = [[userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey] intValue];
    double duration = [[userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey] doubleValue];
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    if (window) {
        kbEndRect = [window convertRect:kbEndRect toView:self.view];
    }
    [UIView beginAnimations:@"ScrollContraint" context:nil];
    [UIView setAnimationDuration:duration];
    [UIView setAnimationCurve:animCurve];
    [UIView setAnimationBeginsFromCurrentState:YES];
    
    self.constraintScrollBottom.constant = 0.0f;
    [self.view layoutIfNeeded];
    
    [UIView commitAnimations];
}

- (void)userInfoUpdate:(NSNotification *)noti {
    [self initView];
}
@end
