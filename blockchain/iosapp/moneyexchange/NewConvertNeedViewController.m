//
//  NewConvertNeedViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "NewConvertNeedViewController.h"
#import "SYSRecommendViewController.h"
#import "CommitNewNeedViewController.h"
#import "QKLServerClient.h"
#import "QKLUtils.h"
#import "QKLConvertInfoNode.h"
#import "QKLUserLoginViewController.h"
#import "QKLCompleteInfoViewController.h"

@interface NewConvertNeedViewController ()<UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate> {
    SysRecFlag sysRecFlag;
    NSMutableArray *itemList;
    ConvertMoneyType currencyType;
}

@end

@implementation NewConvertNeedViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    [self setSelectCurrency:ConvertMoneyTypeUSD];
}

#pragma mark func
- (void)initView {
    sysRecFlag = SysRecFlagNone;
    itemList = [NSMutableArray array];
    [self.view addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyBoard)]];
}

- (void)hideKeyBoard {
    [self.view endEditing:YES];
}
#pragma mark UIPickerViewDelegate, UIPickerViewDataSource
-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    return 2;
}

-(UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view {
    UILabel *label = (UILabel *)view;
    if (!label) {
        label = [UILabel new];
        [label setFont:[UIFont systemFontOfSize:15.0f]];
        [label setTextColor:[UIColor colorWithRGB:0x5A5A5A]];
    }
    if (row == 0) {
        label.text = @"人民币";
    } else {
        label.text = @"美元";
    }
    
    return label;
}

-(CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component {
    return 30.0f;
}

-(CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component {
    return CGRectGetWidth(pickerView.frame);
}

#pragma mark UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self hideKeyBoard];
    return YES;
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    return [self validateNumber:string];
}

- (BOOL)validateNumber:(NSString*)number {
    BOOL res = YES;
    NSCharacterSet* tmpSet = [NSCharacterSet characterSetWithCharactersInString:@"0123456789."];
    int i = 0;
    while (i < number.length) {
        NSString * string = [number substringWithRange:NSMakeRange(i, 1)];
        NSRange range = [string rangeOfCharacterFromSet:tmpSet];
        if (range.length == 0) {
            res = NO;
            break;
        }
        i++;
    }
    return res;
}

#pragma mark click handler
- (IBAction)clickNextStep:(id)sender {
    if (_tfCount.text.length == 0 || [_tfCount.text floatValue] <= 0.0f) {
        [self.view makeToast:lang_enter_correct_num];
        return;
    }
    
    CGFloat count = [_tfCount.text floatValue];
    QKLSumOfMoneyNode *node;
    if (currencyType == ConvertMoneyTypeCNY) {
        node = [[QKLUserManager shareInstanceWithVC:nil] getCNYSumNode];
        if (node) {
            if (count < node.min || count > node.max) {
                [self.view makeToast:[NSString stringWithFormat:lang_enter_correct_sum, node.min, node.max]];
                return;
            }
        }
    } else if (currencyType == ConvertMoneyTypeUSD) {
        node = [[QKLUserManager shareInstanceWithVC:nil] getUSDSumNode];
        if (node) {
            if (count < node.min || count > node.max) {
                 [self.view makeToast:[NSString stringWithFormat:lang_enter_correct_sum, node.min, node.max]];
                return;
            }
        }
    }
    
    [self hideKeyBoard];
    [itemList removeAllObjects];
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_sysrecom, tag_stype: @(currencyType), tag_money: @([_tfCount.text floatValue])}] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            NSDictionary *data = [response objectForKey:tag_data];
            if (data) {
                NSLog(@"%@",data);
                sysRecFlag = [[data objectForKey:tag_sflag] integerValue];
                if ([data containsObjectForKey:tag_itemlist]) {
                    NSArray *itemArr = [data objectForKey:tag_itemlist];
                    for (NSDictionary *item in itemArr) {
                        [itemList addObject:[[QKLConvertInfoNode alloc] initWithDict:item]];
                    }
                }
            }
            [self reloadView];
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        [self.view makeToast:lang_net_err];
    } showHUD:self.view];
}

- (void)reloadView {
    UIViewController *vc;
    if (![[QKLUserManager shareInstanceWithVC:self] hasLoggin]) {
        vc = [[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil];
    } else {
        QKLUserInfo *userInfo = [[QKLUserManager shareInstanceWithVC:nil] getLoginUserInfo];
        if (![userInfo isPaypalCorrect] || ![userInfo isAlipayCorrect]) {
            vc = [[QKLCompleteInfoViewController alloc] initWithNibName:@"QKLCompleteInfoViewController" bundle:nil];
        }
    }
    if (vc) {
        [self presentViewController:vc animated:YES completion:nil];
        return;
    }
    switch (sysRecFlag) {
        case SysRecFlagNone:
        {
            [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_itemadd, tag_stype: @(currencyType), tag_money: @([_tfCount.text floatValue])}] success:^(id response) {
                if ([QKLUtils getResponseCode:response] == 0) {
                    NSDictionary *data = [response objectForKey:tag_data];
                    if (data) {
                        NSLog(@"%@",data);
                        CommitNewNeedViewController *commitVC = [[CommitNewNeedViewController alloc] initWithNibName:@"CommitNewNeedViewController" bundle:nil];
                        commitVC.moneyType = currencyType;
                        commitVC.money = [_tfCount.text floatValue];
                        commitVC.itemaddResult = data;
                        [self.navigationController pushViewController:commitVC animated:YES];
                    }
                    
                } else {
                    [self.view makeToast:[QKLUtils getResponseMsg:response]];
                }
            } fail:^(NSError *error) {
                [self.view makeToast:lang_net_err];
            } showHUD:self.view];
            
        }
            break;
        case SysRecFlagEqual:
        {
            
        }
//            break;
        case SysRecFlagAddEqual:
        {
            
        }
//            break;
        case SysRecFlagAddFivePer:
        {
            
        }
//            break;
            
        default:
        {
            SYSRecommendViewController *sysRe = [[SYSRecommendViewController alloc] initWithNibName:@"SYSRecommendViewController" bundle:nil];
            sysRe.itemList = [itemList copy];
            sysRe.sysRecFlag = sysRecFlag;
            sysRe.needmoneyType = currencyType;
            sysRe.needMoney = [_tfCount.text floatValue];
            [self.navigationController pushViewController:sysRe animated:YES];
        }
            break;
    }
}

- (IBAction)clickBack:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)clickCurrencyBtn:(id)sender {
    UIAlertController *currencySheet = [UIAlertController alertControllerWithTitle:nil message:nil preferredStyle:UIAlertControllerStyleActionSheet];
    UIAlertAction *CNYAction = [UIAlertAction actionWithTitle:@"人民币" style:UIAlertActionStyleDestructive handler:^(UIAlertAction *action) {
        [self setSelectCurrency:ConvertMoneyTypeCNY];
    }];
    
    UIAlertAction *USDAction = [UIAlertAction actionWithTitle:@"美元" style:UIAlertActionStyleDestructive handler:^(UIAlertAction *action) {
        [self setSelectCurrency:ConvertMoneyTypeUSD];
    }];
    
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction *action) {
        
    }];
    
    [currencySheet addAction:USDAction];
    [currencySheet addAction:CNYAction];
    [currencySheet addAction:cancelAction];
    
    [self presentViewController:currencySheet animated:YES completion:nil];

}

-(void)setSelectCurrency:(ConvertMoneyType)moneyType {
    switch (moneyType) {
        case ConvertMoneyTypeUSD:
        {
            [_currencyBtn setTitle:@"美元" forState:UIControlStateNormal];
            currencyType = ConvertMoneyTypeUSD;
        }
            break;
        case ConvertMoneyTypeCNY:
        {
            [_currencyBtn setTitle:@"人民币" forState:UIControlStateNormal];
            currencyType = ConvertMoneyTypeCNY;
        }
            break;
        default:
            break;
    }
}
@end
