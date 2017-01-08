//
//  ConvertDetailTableViewCell.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "ConvertDetailTableViewCell.h"
#import "QKLUtils.h"

@implementation ConvertDetailTableViewCell {
    QKLConvertInfoNode *infoNode;
}

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)configCellWithInfo:(QKLConvertInfoNode *)node {
    infoNode = node;
    if (node.stype == ConvertMoneyTypeCNY) {
        _ivConvertType.image = [UIImage imageNamed:@"img_rmb"];
        _labelPreMoney.text = [NSString stringWithFormat:@"￥%.2f", node.money];
        _labelFinalMoney.text = [NSString stringWithFormat:@"$%.2f", node.cost];
        _labelConvertTypeDesc.text = lang_convert_rmb_to_dollor;
    } else {
        _ivConvertType.image = [UIImage imageNamed:@"img_doller"];
        _labelPreMoney.text = [NSString stringWithFormat:@"$%.2f", node.money];
        _labelFinalMoney.text = [NSString stringWithFormat:@"￥%.2f", node.cost];
        _labelConvertTypeDesc.text = lang_convert_doller_to_rmb;
    }
    _labelCreatedTime.text = [QKLUtils getUpdateTimeByTime:node.createdate isInSecond:NO];
    if (self.cellStyle == ConvertDetailCellStyleNoAction) {
        _btnConvert.hidden = YES;
        _labelChargeState.hidden = YES;
    } else if (self.cellStyle == ConvertDetailCellStyleMineNeed) {
        _btnConvert.hidden = NO;
        _labelChargeState.hidden = YES;
        [self.btnConvert setTitle:@"删除" forState:UIControlStateNormal];
        [self.btnConvert setTintColor:[UIColor redColor]];
    } else if (self.cellStyle == ConvertDetailCellStyleTransferStatus) {
        _btnConvert.hidden = YES;
        _labelChargeState.hidden = NO;
    } else{
        _btnConvert.hidden = NO;
        _labelChargeState.hidden = YES;
    }
}

- (IBAction)clickConvert:(id)sender {
//    if (infoNode.sflag == ItemListTypeFirstPage) {
//        if (_delegate && [_delegate respondsToSelector:@selector(userPerformConvertInfo:)]) {
//            [_delegate userPerformConvertInfo:infoNode];
//        }
//    } else if (infoNode.sflag == ItemListTypeMineNeed) {
//        if (_delegate && [_delegate respondsToSelector:@selector(userPerformDeleteInfo:)]) {
//            [_delegate userPerformDeleteInfo:infoNode];
//        }
//    } else if (infoNode.sflag == ItemListTypeTransferHis) {
//        if (_delegate && [_delegate respondsToSelector:@selector(userPerformTransferingInfo:)]) {
//            [_delegate userPerformTransferingInfo:infoNode];
//        }
//    }
    if (self.cellStyle == ConvertDetailCellStyleDefault) {
        if (_delegate && [_delegate respondsToSelector:@selector(userPerformConvertInfo:)]) {
            [_delegate userPerformConvertInfo:infoNode];
        }
    } else if (self.cellStyle == ConvertDetailCellStyleMineNeed) {
        if (_delegate && [_delegate respondsToSelector:@selector(userPerformDeleteInfo:)]) {
            [_delegate userPerformDeleteInfo:infoNode];
        }
    } else if (self.cellStyle == ConvertDetailCellStyleTransferStatus) {
        if (_delegate && [_delegate respondsToSelector:@selector(userPerformTransferingInfo:)]) {
            [_delegate userPerformTransferingInfo:infoNode];
        }
    }
}
@end
