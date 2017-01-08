//
//  SectionFooterActionTableViewCell.h
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, ActionType) {
    ActionTypeSureConvert = 0,
    ActionTypeCommitNeed = 1
};

@protocol SectionFooterActionDelegate <NSObject>

- (void)sectionFooterAction:(ActionType)actionType;

@end

@interface SectionFooterActionTableViewCell : UITableViewCell

@property (weak, nonatomic) id<SectionFooterActionDelegate> delegate;

@property (assign, nonatomic) ActionType actionType;

@property (weak, nonatomic) IBOutlet UIButton *btnAction;

- (IBAction)clickAction:(id)sender;

@end
