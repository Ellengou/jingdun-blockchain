//
//  ReceivedAccountDetailTableViewCell.m
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "ReceivedAccountDetailTableViewCell.h"
#import "QKLUserManager.h"

@implementation ReceivedAccountDetailTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];
}

- (void)configCellType:(ConvertMoneyType)type money:(CGFloat)money {
    QKLUserInfo *userInfo = [[QKLUserManager shareInstanceWithVC:nil] getLoginUserInfo];
    if (type == ConvertMoneyTypeCNY) {
        QKLPayAccountInfoNode *node = [userInfo isAlipayCorrect];
        if (node) {
            _labelPlatform.text = [NSString stringWithFormat:@"支付宝： %@", node.carnum];
            _labelAccountName.text = [NSString stringWithFormat:@"姓名： %@", node.sname];
            _labelRelatedMoney.text = [NSString stringWithFormat:@"转入金额： ￥%.2f", money];
        }
    } else {
         QKLPayAccountInfoNode *node = [userInfo isPaypalCorrect];
        if (node) {
            _labelPlatform.text = [NSString stringWithFormat:@"PayPal： %@", node.carnum];
            _labelAccountName.text = [NSString stringWithFormat:@"姓名： %@", node.sname];
            _labelRelatedMoney.text = [NSString stringWithFormat:@"转入金额： $%.2f", money];
        }
    }
}
@end
