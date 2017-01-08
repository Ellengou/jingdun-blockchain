//
//  SYSRecommendViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"
#import "NewConvertNeedViewController.h"
#import "QKLServerClient.h"

@interface SYSRecommendViewController : QKLViewController

@property (nonatomic, assign) ConvertMoneyType needmoneyType;

@property (nonatomic, assign) CGFloat needMoney;

@property (strong, nonatomic) NSArray *itemList;

@property (assign, nonatomic) SysRecFlag sysRecFlag;

@property (weak, nonatomic) IBOutlet UITableView *tableSysRecommend;

- (IBAction)clickBack:(id)sender;
@end
