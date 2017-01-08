//
//  QKLUserLoginViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

extern NSString *const QKLUserHasLogInNotification;

@interface QKLUserLoginViewController : QKLViewController

@property (weak, nonatomic) IBOutlet UITextField *tfPhoneNumber;

@property (weak, nonatomic) IBOutlet UITextField *tfPassWord;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintIconTop;

- (IBAction)clickLogin:(id)sender;

- (IBAction)clickRegister:(id)sender;

- (IBAction)clickForgetPassWord:(id)sender;

- (IBAction)clickBack:(id)sender;

@end
