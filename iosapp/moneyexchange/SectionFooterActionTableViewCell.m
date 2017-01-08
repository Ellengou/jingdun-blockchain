//
//  SectionFooterActionTableViewCell.m
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "SectionFooterActionTableViewCell.h"

@implementation SectionFooterActionTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];
}

- (IBAction)clickAction:(id)sender {
    if (_delegate && [_delegate respondsToSelector:@selector(sectionFooterAction:)]) {
        [_delegate sectionFooterAction:_actionType];
    }
}
@end
