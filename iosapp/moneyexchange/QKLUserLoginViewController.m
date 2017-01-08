//
//  QKLUserLoginViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLUserLoginViewController.h"
#import "QKLUserRegisterViewController.h"
#import "QKLUserChangePWViewController.h"
#import "QKLUtils.h"


@interface QKLUserLoginViewController ()<UITextFieldDelegate>

@end

@implementation QKLUserLoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userHasLogin:) name:QKLUserHasLogInNotification object:nil];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:QKLUserHasLogInNotification object:nil];
}

#pragma mark func
- (void)initView {
    [self.view addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyBoard)]];
}

- (void)hideKeyBoard {
    [self.view endEditing:YES];
}

#pragma mark click handler
- (IBAction)clickLogin:(id)sender {
    if (_tfPhoneNumber.text.length && _tfPassWord.text.length) {
        [[QKLUserManager shareInstanceWithVC:self] loginWithAccount:_tfPhoneNumber.text password:_tfPassWord.text];
    } else {
        [self.view makeToast:lang_account_password];
    }
}

- (IBAction)clickRegister:(id)sender {
    [self presentViewController:[[QKLUserRegisterViewController alloc] initWithNibName:@"QKLUserRegisterViewController" bundle:nil] animated:YES completion:^{
        [self hideKeyBoard];
    }];
}

- (IBAction)clickForgetPassWord:(id)sender {
    [self presentViewController:[[QKLUserChangePWViewController alloc] initWithNibName:@"QKLUserChangePWViewController" bundle:nil] animated:YES completion:^{
        [self hideKeyBoard];
    }];
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

#pragma mark notificationn handler
- (void)userHasLogin:(NSNotification *)noti {
    [self clickBack:nil];
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
    
    self.constraintIconTop.constant = -60.0f;
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
    
    self.constraintIconTop.constant = 110.0f;
    [self.view layoutIfNeeded];
    
    [UIView commitAnimations];
    
}
@end
