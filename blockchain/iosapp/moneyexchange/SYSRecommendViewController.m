//
//  SYSRecommendViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "SYSRecommendViewController.h"
#import "SectionFooterActionTableViewCell.h"
#import "ConvertDetailTableViewCell.h"
#import "QKLUserLoginViewController.h"
#import "QKLCompleteInfoViewController.h"
#import "ConvertActionViewController.h"
#import "CommitNewNeedViewController.h"
#import "QKLUtils.h"
#import "QKLServerClient.h"

@interface SYSRecommendViewController ()<UITableViewDelegate, UITableViewDataSource, SectionFooterActionDelegate, ConvertDetailTableViewCellDelegate>

@end

@implementation SYSRecommendViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)initView {
  
}


#pragma mark UITableViewDelegate, UITableViewDataSource
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (_itemList.count) {
        return _itemList.count + 1;
    }
    return _itemList.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == _itemList.count) {
        return 120.0f;
    }
    return 122.0f;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return [[UIViewController alloc] initWithNibName:@"SectionHeaderDescTableViewCell" bundle:nil].view;
}


-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 45.0f;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == _itemList.count) {
        SectionFooterActionTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"SectionFooterActionTableViewCellID"];
        if (!cell) {
            UIViewController *vc = [[UIViewController alloc] initWithNibName:@"SectionFooterActionTableViewCell" bundle:nil];
            cell = (SectionFooterActionTableViewCell *)vc.view;
            cell.delegate = self;
        }
        if (_sysRecFlag == SysRecFlagAddFivePer) {
            cell.actionType = ActionTypeCommitNeed;
            [cell.btnAction setTitle:@"跳过此步骤，发布需求" forState:UIControlStateNormal];
        }else{
            cell.actionType = ActionTypeSureConvert;
        }
        return cell;
    }
    ConvertDetailTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ConvertDetailTableViewCellID"];
    if (!cell) {
        UIViewController *vc = [[UIViewController alloc] initWithNibName:@"ConvertDetailTableViewCell" bundle:nil];
        cell = (ConvertDetailTableViewCell *)vc.view;
        cell.delegate = self;
    }
    if (_sysRecFlag == SysRecFlagAddFivePer) {
        cell.cellStyle = ConvertDetailCellStyleDefault;
    }else{
        cell.cellStyle = ConvertDetailCellStyleNoAction;
    }
    [cell configCellWithInfo:[_itemList objectAtIndex:indexPath.row]];
//    cell.btnConvert.hidden = YES;

    return cell;
}

#pragma mark SectionFooterActionDelegate method
- (void)sectionFooterAction:(ActionType)actionType {
    switch (actionType) {
        case ActionTypeSureConvert:
        {
            [self convert:[_itemList copy]];
        }
            break;
        case ActionTypeCommitNeed:
        {
            [self commitNewNeed];
        }
            break;
        default:
            break;
    }
}

#pragma mark - ConvertDetailTableViewCellDelegate
- (void)userPerformConvertInfo:(QKLConvertInfoNode *)node {
    [self convert:[NSMutableArray arrayWithObject:node]];
}

#pragma mark click handler
- (IBAction)clickBack:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)convert:(NSArray<QKLConvertInfoNode *> *)infoNodeList {
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
    ConvertActionViewController *Cvc = [[ConvertActionViewController alloc] initWithNibName:@"ConvertActionViewController" bundle:nil];
    Cvc.infoNodeList = infoNodeList;
    [self.navigationController pushViewController:Cvc animated:YES];
}

- (void)commitNewNeed {
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
    
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_itemadd, tag_stype: @(_needmoneyType), tag_money: @(_needMoney)}] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            NSDictionary *data = [response objectForKey:tag_data];
            if (data) {
                NSLog(@"%@",data);
                CommitNewNeedViewController* commitNewNeedVC = [[CommitNewNeedViewController alloc] initWithNibName:@"CommitNewNeedViewController" bundle:nil];
                commitNewNeedVC.money = _needMoney;
                commitNewNeedVC.moneyType = _needmoneyType;
                commitNewNeedVC.itemaddResult = data;
                [self.navigationController pushViewController:commitNewNeedVC animated:YES];
            }
            
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
        [self.view makeToast:lang_net_err];
    } showHUD:self.view];
}

@end
