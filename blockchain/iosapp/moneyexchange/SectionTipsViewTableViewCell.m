//
//  SectionTipsViewTableViewCell.m
//  moneyexchange
//
//  Created by gaohongwei on 16/10/30.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "SectionTipsViewTableViewCell.h"
@interface SectionTipsViewTableViewCell (){
    
}
@property (weak, nonatomic) IBOutlet UILabel *tipsLabel;

@end

@implementation SectionTipsViewTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
