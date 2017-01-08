//
//  ReceivedAccountDetailTableViewCell.h
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QKLServerClient.h"

@interface ReceivedAccountDetailTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *labelPlatform;

@property (weak, nonatomic) IBOutlet UILabel *labelAccountName;

@property (weak, nonatomic) IBOutlet UILabel *labelRelatedMoney;

- (void)configCellType:(ConvertMoneyType)type money:(CGFloat)money;
@end
