//
//  ConvertDetailTableViewCell.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QKLConvertInfoNode.h"

typedef NS_ENUM(NSInteger, ConvertDetailCellStyle) {
    ConvertDetailCellStyleDefault = 0, //默认 就是展示 兑换
    ConvertDetailCellStyleMineNeed = 1, //我的需求列表中展示 action为删除
    ConvertDetailCellStyleTransferStatus = 2, //交易记录列表中展示 action状态根据订单状态细分
    ConvertDetailCellStyleNoAction = 3, //没有action btn隐藏
};

@protocol ConvertDetailTableViewCellDelegate <NSObject>

@optional
- (void)userPerformConvertInfo:(QKLConvertInfoNode *)node;

- (void)userPerformDeleteInfo:(QKLConvertInfoNode *)node;

- (void)userPerformTransferingInfo:(QKLConvertInfoNode *)node;

@end

@interface ConvertDetailTableViewCell : UITableViewCell

@property (assign, nonatomic) ConvertDetailCellStyle cellStyle;

@property (weak, nonatomic) id<ConvertDetailTableViewCellDelegate> delegate;

@property (weak, nonatomic) IBOutlet UIImageView *ivConvertType;

@property (weak, nonatomic) IBOutlet UILabel *labelPreMoney;

@property (weak, nonatomic) IBOutlet UILabel *labelConvertTypeDesc;

@property (weak, nonatomic) IBOutlet UILabel *labelFinalMoney;

@property (weak, nonatomic) IBOutlet UILabel *labelCreatedTime;

@property (weak, nonatomic) IBOutlet UILabel *labelChargeState;

@property (weak, nonatomic) IBOutlet UIButton *btnConvert;

- (IBAction)clickConvert:(id)sender;

- (void)configCellWithInfo:(QKLConvertInfoNode *)node;
@end
