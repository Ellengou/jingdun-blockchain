//
//  QKLUserChangePWViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

@interface QKLUserChangePWViewController : QKLViewController

@property (weak, nonatomic) IBOutlet UIButton *btnCountDown;

@property (weak, nonatomic) IBOutlet UITextField *tfPhoneNumber;

@property (weak, nonatomic) IBOutlet UITextField *tfVertifyCode;

@property (weak, nonatomic) IBOutlet UITextField *tfNewPassWord;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintIconTop;

- (IBAction)clickVertifyCode:(id)sender;

- (IBAction)clickSubmit:(id)sender;

- (IBAction)clickBack:(id)sender;

@end
