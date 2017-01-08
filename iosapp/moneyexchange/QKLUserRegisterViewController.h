//
//  QKLUserRegisterViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

@interface QKLUserRegisterViewController : QKLViewController

@property (weak, nonatomic) IBOutlet UITextField *tfPhoneNumber;

@property (weak, nonatomic) IBOutlet UITextField *tfVerifyCode;

@property (weak, nonatomic) IBOutlet UITextField *tfPassWord;

@property (weak, nonatomic) IBOutlet UITextField *tfCheckPassWord;

@property (weak, nonatomic) IBOutlet UIButton *btnCountDown;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintIconTop;

- (IBAction)clickVertifyCode:(id)sender;

- (IBAction)clickRegister:(id)sender;

- (IBAction)clickBack:(id)sender;

@end
