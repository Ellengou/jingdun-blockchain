//
//  NewConvertNeedViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

typedef NS_ENUM(NSInteger, SysRecFlag) {
    SysRecFlagNone = 0,
    SysRecFlagEqual = 1,
    SysRecFlagAddEqual = 2,
    SysRecFlagAddFivePer = 3
};

@interface NewConvertNeedViewController : QKLViewController

@property (weak, nonatomic) IBOutlet UIPickerView *pickerView;

@property (weak, nonatomic) IBOutlet UIButton *currencyBtn;

@property (weak, nonatomic) IBOutlet UITextField *tfCount;


- (IBAction)clickNextStep:(id)sender;

- (IBAction)clickBack:(id)sender;
@end
