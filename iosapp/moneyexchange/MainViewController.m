//
//  MainViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/14.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "MainViewController.h"
#import "LeftMenuViewController.h"
#import "NewConvertNeedViewController.h"
#import "ConvertDetailTableViewCell.h"
#import "ConvertActionViewController.h"
#import "QKLUtils.h"
#import "QKLServerClient.h"
#import <MJRefresh.h>
#import "QKLConvertInfoNode.h"

@interface MainViewController ()<UIScrollViewDelegate, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate, UIGestureRecognizerDelegate, ConvertDetailTableViewCellDelegate> {
    BOOL isMenuShowed;
    CGPoint originalOrigin;
    UIView *menuView;
    
    BOOL isRequesting;
    NSInteger currentPageNum;
    NSInteger totalPageNum;
    
    NSMutableArray *arrForTable;
    NSMutableArray *arrForNet;
}

@end

@implementation MainViewController

#pragma mark viewController cycle
- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
    
}

-(void)viewDidLayoutSubviews {
    
}

- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [_tableContent.mj_header beginRefreshing];
}

#pragma mark func method
-(void)initView {
    CGFloat screenWidth = CGRectGetWidth([UIScreen mainScreen].bounds);
    _constraintContentViewWidth.constant = screenWidth;
    _constraintMenuContainerTrailing.constant = screenWidth / 3.0f;
    LeftMenuViewController *menuVC = [[LeftMenuViewController alloc] initWithNibName:@"LeftMenuViewController" bundle:nil];
    menuVC.parentVC = self;
    [self addChildViewController:menuVC];
    menuView = menuVC.view;
    menuView.frame = _viewMenuContainer.bounds;
    [_viewMenuContainer addSubview:menuView];
    [self.viewContentMask addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(nowPlayingMainViewTapped:)]];
    
    
    UIPanGestureRecognizer *panRecognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self
                                                                                    action:@selector(nowPlayingMainViewPanned:)];
    [panRecognizer setDelegate:self];
    [self.viewContentContainer addGestureRecognizer:panRecognizer];
    [self.viewContentMask addGestureRecognizer:[[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(nowPlayingMainViewPanned:)]];
    [_flagType setOn:NO];
    _tfSearch.placeholder = @"搜索金额";
    isRequesting = NO;
    currentPageNum = 1;
    totalPageNum = 1;
    arrForTable = [NSMutableArray array];
    arrForNet = [NSMutableArray array];
    _tableContent.keyboardDismissMode = UIScrollViewKeyboardDismissModeOnDrag;
    __unsafe_unretained UITableView *tableView = self.tableContent;
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
            [_tableContent.mj_header endRefreshing];
        } else {
            if (currentPageNum > totalPageNum) {
                [self.view makeToast:lang_no_more];
            }
            [_tableContent.mj_footer endRefreshing];
        }
        return;
    }
    isRequesting = YES;
    [arrForNet removeAllObjects];
    NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithDictionary:@{method_sel: method_sel_itemlist, tag_pagenum: @(currentPageNum), tag_pagesize: QKL_DEFAULT_ITEM_COUNT, tag_stype: @(_flagType.isOn), tag_sflag: @(ItemListTypeFirstPage)}];
    if (_tfSearch.text.length) {
        [params setObject:_tfSearch.text forKey:tag_keyword];
    }
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
            [_tableContent.mj_header endRefreshing];
        } else {
            [_tableContent.mj_footer endRefreshing];
        }
    } fail:^(NSError *error) {
        isRequesting = NO;
        [self.view makeToast:lang_net_err];
        if (state == QKLRequestStateReload) {
            [_tableContent.mj_header endRefreshing];
        } else {
            [_tableContent.mj_footer endRefreshing];
        }
    } showHUD:nil];
}

- (void)reloadTableViewWithState:(QKLRequestState)state {
    //从arrForNet初始化arrForTable
    if (state == QKLRequestStateReload) {
        [arrForTable removeAllObjects];
        if (arrForNet.count == 0) {
            _tableContent.tableFooterView = [QKLUtils getNoDataTableFooterWithHeight:200.0f msg:lang_no_record_data bgColor:[UIColor clearColor]];
        } else {
            _tableContent.tableFooterView = nil;
        }
    }
    [arrForTable addObjectsFromArray:arrForNet];
    [_tableContent reloadData];
}

#pragma mark - UIGestureRecognize actions
- (void)nowPlayingMainViewTapped:(id)sender {
    [self clickShowHideMenu:_btnMenu];
}

- (void)nowPlayingMainViewPanned:(id)sender {
    UIPanGestureRecognizer *recognizer = sender;
    UIView *view;
    if (isMenuShowed) {
        view = _viewContentMask;
    } else {
        view = _viewContentContainer;
    }
    if(recognizer.state == UIGestureRecognizerStateBegan) {
        originalOrigin = CGPointMake(_constraintContentViewLeading.constant, 0);
    }
    
    CGPoint translatedPoint = [recognizer translationInView:view];
    
    CGFloat final = originalOrigin.x + translatedPoint.x;
    
    float alpha =  0.5f  * final  / self.viewMenuContainer.frame.size.width;// + 0.5;
    if (final <= 0) {
        final = 0;
        alpha =  0.0f;
    }
    
    if (final >= self.viewMenuContainer.frame.size.width) {
       final = self.viewMenuContainer.frame.size.width;
        alpha = 0.5f;
    }
    _constraintContentViewLeading.constant = final;
    self.viewContentMask.alpha = alpha;
    [_viewContentContainer layoutIfNeeded];
    if(recognizer.state == UIGestureRecognizerStateEnded) {
        [self clickShowHideMenu:nil];
    }
}

#pragma mark - UIGestureRecognizerDelegate methods
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch {
    return YES;
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer {
    return NO;
}

- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer
{
    return !isMenuShowed;
}


#pragma mark clicked handler
- (IBAction)clickShowHideMenu:(id)sender {
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.3];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    
    CGFloat leading = _constraintContentViewLeading.constant;
    if (nil != sender) {
        if (leading == 0) {
            isMenuShowed = YES;
        } else {
            isMenuShowed = NO;
        }
    } else {
        int half = CGRectGetWidth(self.viewMenuContainer.frame) / 2;
        if (_constraintContentViewLeading.constant < half) {
            isMenuShowed = NO;
        } else {
            isMenuShowed = YES;
        }
    }
    
    if (isMenuShowed) {
        leading = CGRectGetWidth(self.viewMenuContainer.frame);
        self.viewContentMask.alpha = 0.5f;
    } else {
        leading = 0;
        self.viewContentMask.alpha = 0.0f;
    }
    
    _constraintContentViewLeading.constant = leading;
    [_viewContentContainer layoutIfNeeded];
    [UIView commitAnimations];
}

- (IBAction)clickAddNewConvert:(id)sender {
    if (![[QKLUserManager shareInstanceWithVC:self] hasLoggin]) {
        QKLUserLoginViewController *vc = [[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil];
        [self presentViewController:vc animated:YES completion:nil];
    } else {
        [self.navigationController pushViewController:[[NewConvertNeedViewController alloc] initWithNibName:@"NewConvertNeedViewController" bundle:nil] animated:YES];
    }
}

- (IBAction)clickSwitchConvertRule:(id)sender {
    [_tableContent.mj_header beginRefreshing];
}

#pragma mark UITableViewDelegate, UITableViewDataSource
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
    }
    [cell configCellWithInfo:[arrForTable objectAtIndex:indexPath.row]];
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

#pragma mark textField delegate
-(void)textFieldDidEndEditing:(UITextField *)textField {
    [self.view endEditing:YES];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [_tableContent.mj_header beginRefreshing];
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

#pragma mark ConvertDetailTableViewCellDelegate
-(void)userPerformConvertInfo:(QKLConvertInfoNode *)node {
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
    Cvc.infoNodeList = @[node];
    [self.navigationController pushViewController:Cvc animated:YES];
}
@end
