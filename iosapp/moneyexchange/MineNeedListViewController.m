//
//  MineNeedListViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "MineNeedListViewController.h"
#import "QKLServerClient.h"
#import "QKLUtils.h"
#import "QKLUserManager.h"
#import "ConvertDetailTableViewCell.h"
#import <MJRefresh.h>
#import "QKLServerClient.h"
#import "QKLUserLoginViewController.h"
#import "QKLCompleteInfoViewController.h"

@interface MineNeedListViewController ()<UITableViewDelegate, UITableViewDataSource, ConvertDetailTableViewCellDelegate> {
    BOOL isRequesting;
    NSInteger currentPageNum;
    NSInteger totalPageNum;
    
    NSMutableArray *arrForTable;
    NSMutableArray *arrForNet;
}

@end

@implementation MineNeedListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

- (void)initView {
    isRequesting = NO;
    currentPageNum = 1;
    totalPageNum = 1;
    arrForTable = [NSMutableArray array];
    arrForNet = [NSMutableArray array];
    _tableMineNeed.keyboardDismissMode = UIScrollViewKeyboardDismissModeOnDrag;
    __unsafe_unretained UITableView *tableView = _tableMineNeed;
    // 下拉刷新
    tableView.mj_header= [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        currentPageNum = 1;
        totalPageNum = 1;
        [self requestDataWithData:QKLRequestStateReload];
    }];
    // 设置自动切换透明度(在导航栏下面自动隐藏)
    tableView.mj_header.automaticallyChangeAlpha = YES;
    
    // 上拉刷新
    tableView.mj_footer = [MJRefreshBackNormalFooter footerWithRefreshingBlock:^{
        currentPageNum ++;
        [self requestDataWithData:QKLRequestStateAppend];
    }];
    
    [tableView.mj_header beginRefreshing];
}

- (void)requestDataWithData:(QKLRequestState)state {
    if (isRequesting || currentPageNum > totalPageNum) {
        if (state == QKLRequestStateReload) {
            [_tableMineNeed.mj_header endRefreshing];
        } else {
            if (currentPageNum > totalPageNum) {
                [self.view makeToast:lang_no_more];
            }
            [_tableMineNeed.mj_footer endRefreshing];
        }
        return;
    }
    isRequesting = YES;
    [arrForNet removeAllObjects];
    NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithDictionary:@{method_sel: method_sel_itemlist, tag_pagenum: @(currentPageNum), tag_pagesize: QKL_DEFAULT_ITEM_COUNT, tag_sflag: @(ItemListTypeMineNeed)}];
    
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:[params copy]] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            NSDictionary *data = [response objectForKey:tag_data];
            totalPageNum = [[data objectForKey:tag_totalpage] integerValue];
            NSArray *itemList = [data objectForKey:tag_itemlist];
            for (NSDictionary *dict in itemList) {
                QKLConvertInfoNode *node = [[QKLConvertInfoNode alloc] initWithDict:dict];
                [arrForNet addObject:node];
            }
            [self reloadTableViewWithState:state];
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
        isRequesting = NO;
        if (state == QKLRequestStateReload) {
            [_tableMineNeed.mj_header endRefreshing];
        } else {
            [_tableMineNeed.mj_footer endRefreshing];
        }
    } fail:^(NSError *error) {
        isRequesting = NO;
        [self.view makeToast:lang_net_err];
        if (state == QKLRequestStateReload) {
            [_tableMineNeed.mj_header endRefreshing];
        } else {
            [_tableMineNeed.mj_footer endRefreshing];
        }
    } showHUD:nil];
}

- (void)reloadTableViewWithState:(QKLRequestState)state {
    //从arrForNet初始化arrForTable
    if (state == QKLRequestStateReload) {
        [arrForTable removeAllObjects];
        if (arrForNet.count == 0) {
            _tableMineNeed.tableFooterView = [QKLUtils getNoDataTableFooterWithHeight:200.0f msg:lang_no_record_data bgColor:[UIColor clearColor]];
        } else {
            _tableMineNeed.tableFooterView = nil;
        }
    }
    [arrForTable addObjectsFromArray:arrForNet];
    [_tableMineNeed reloadData];
}

#pragma mark tableview datasource delegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return arrForTable.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 122.0f;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ConvertDetailTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ConvertDetailTableViewCellID"];
    if (!cell) {
        UIViewController *vc = [[UIViewController alloc] initWithNibName:@"ConvertDetailTableViewCell" bundle:nil];
        cell = (ConvertDetailTableViewCell *)vc.view;
        cell.delegate = self;
        cell.cellStyle = ConvertDetailCellStyleNoAction;
    }
    QKLConvertInfoNode *node = [arrForTable objectAtIndex:indexPath.row];
    if (node.sstatus == 99) {
        cell.cellStyle = ConvertDetailCellStyleMineNeed;
    }
    [cell configCellWithInfo:[arrForTable objectAtIndex:indexPath.row]];
    return cell;
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

#pragma mark click handler
- (IBAction)clickBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark ConvertDetailTableViewCellDelegate
- (void)userPerformDeleteInfo:(QKLConvertInfoNode *)node {
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
    [[QKLServerClient sharedNetworking] getWithUrl:server_api_default params:[QKLUtils completeUserToken:@{method_sel: method_sel_itemdel, tag_itemid:@(node.itemid)}] success:^(id response) {
        if ([QKLUtils getResponseCode:response] == 0) {
            [self requestDataWithData:QKLRequestStateReload];
        } else {
            [self.view makeToast:[QKLUtils getResponseMsg:response]];
        }
    } fail:^(NSError *error) {
         [self.view makeToast:lang_net_err];
    } showHUD:nil];

}

@end
