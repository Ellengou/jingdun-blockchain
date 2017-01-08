//
//  QKLCompleteInfoViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

extern NSString *const QKLUserHasLogInNotification;

@interface QKLCompleteInfoViewController : QKLViewController

@property (weak, nonatomic) IBOutlet UIImageView *ivAvatar;

@property (weak, nonatomic) IBOutlet UILabel *labelNickName;

@property (weak, nonatomic) IBOutlet UITextField *tfNickName;

@property (weak, nonatomic) IBOutlet UITextField *tfPaypalAccount;

@property (weak, nonatomic) IBOutlet UITextField *tfPaypalName;

@property (weak, nonatomic) IBOutlet UITextField *tfAlipayAccount;

@property (weak, nonatomic) IBOutlet UITextField *tfAlipayName;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintScrollBottom;

- (IBAction)clickChangeAvatar:(id)sender;

- (IBAction)clickSubmit:(id)sender;

- (IBAction)clickBack:(id)sender;
@end
