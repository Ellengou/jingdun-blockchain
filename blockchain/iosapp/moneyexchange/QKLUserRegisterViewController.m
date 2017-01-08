//
//  QKLUserRegisterViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLUserRegisterViewController.h"
#import "QKLUtils.h"
#import "QKLServerClient.h"

@interface QKLUserRegisterViewController () <UITextFieldDelegate>

@end

@implementation QKLUserRegisterViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [self initView];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
}

#pragma mark func
- (void)initView {
    [self.view addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyBoard)]];
}

- (void)hideKeyBoard {
    [self.view endEditing:YES];
}

#pragma mark click handler
- (IBAction)clickVertifyCode:(id)sender {
    if (!_tfPhoneNumber.text || _tfPhoneNumber.text.length != 11 || ![QKLUtils isPureNumber:_tfPhoneNumber.text]) {
        [self.view makeToast:lang_phone_number_format_err];
        return;
    }
    __block int timeout = 60;
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
    dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0), 1.0*NSEC_PER_SEC, 0);
    dispatch_source_set_event_handler(_timer, ^{
        if(timeout<=0){
            dispatch_source_cancel(_timer);
            dispatch_async(dispatch_get_main_queue(), ^{
                [_btnCountDown setTitle:@"验证码" forState:UIControlStateNormal];
                _btnCountDown.userInteractionEnabled = YES;
            });
        }else{
            dispatch_async(dispatch_get_main_queue(), ^{
                NSLog(@"当前：%d", timeout);
                [_btnCountDown setTitle:[NSString stringWithFormat:@"%d",timeout--] forState:UIControlStateNormal];
                _btnCountDown.userInteractionEnabled = NO;
                
            });
        }
    });
    dispatch_resume(_timer);
    
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_scodeget, tag_username: _tfPhoneNumber.text}] success:^(id response) {
        NSLog(@"%@", response);
        if ([QKLUtils getResponseCode:response] != 0) {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
            dispatch_source_cancel(_timer);
            dispatch_async(dispatch_get_main_queue(), ^{
                [_btnCountDown setTitle:@"验证码" forState:UIControlStateNormal];
                _btnCountDown.userInteractionEnabled = YES;
            });
        }
    } fail:^(NSError *error) {
        [self.view makeToast:lang_net_err];
        dispatch_source_cancel(_timer);
        dispatch_async(dispatch_get_main_queue(), ^{
            [_btnCountDown setTitle:@"验证码" forState:UIControlStateNormal];
            _btnCountDown.userInteractionEnabled = YES;
        });
    } showHUD:nil];
}

- (IBAction)clickRegister:(id)sender {
    if (!_tfPhoneNumber.text || _tfPhoneNumber.text.length != 11 || ![QKLUtils isPureNumber:_tfPhoneNumber.text]) {
        [self.view makeToast:lang_phone_number_format_err];
        return;
    }
    if (!_tfVerifyCode.text || _tfVerifyCode.text.length == 0) {
        [self.view makeToast:lang_not_get_scode];
        return;
    }
    if (!_tfPassWord.text || _tfPassWord.text.length == 0) {
        [self.view makeToast:lang_not_write_password];
        return;
    }
    if (_tfPassWord.text.length < 6 || _tfPassWord.text.length > 18 || ![QKLUtils isAlphaNumeric:_tfPassWord.text]) {
        [self.view makeToast:lang_password_set_err];
        return;
    }
    if (!_tfCheckPassWord.text || _tfCheckPassWord.text.length == 0) {
        [self.view makeToast:lang_not_check_password];
        return;
    }
    if (![_tfCheckPassWord.text isEqualToString:_tfPassWord.text]) {
        [self.view makeToast:lang_not_equal_twice];
        _tfCheckPassWord.text = @"";
        _tfPassWord.text = @"";
        return;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{method_sel:method_sel_user_reister, tag_username: _tfPhoneNumber.text, tag_scode: _tfVerifyCode.text, tag_password: [_tfPassWord.text md5String]}];
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:params] success:^(id response) {
        NSLog(@"%@", response);
        if ([QKLUtils getResponseCode:response] == 0) {
            //目前仅有rc状态返回
            [[QKLUserManager shareInstanceWithVC:self.presentingViewController] loginWithAccount:_tfPhoneNumber.text password:_tfPassWord.text];
            [self clickBack:nil];
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        NSLog(@"%@", error);
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

#pragma mark notification handler
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
    
    self.constraintIconTop.constant = -60.f;
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
    
    self.constraintIconTop.constant = 100.f;
    [self.view layoutIfNeeded];
    
    [UIView commitAnimations];
    
}
@end
