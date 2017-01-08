//
//  MainViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/14.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

@interface MainViewController : QKLViewController

@property (weak, nonatomic) IBOutlet UISwitch *flagType;

@property (weak, nonatomic) IBOutlet UIView *viewMenuContainer;

@property (weak, nonatomic) IBOutlet UIView *viewContentContainer;

@property (weak, nonatomic) IBOutlet UIButton *btnMenu;

@property (weak, nonatomic) IBOutlet UITextField *tfSearch;

@property (weak, nonatomic) IBOutlet UITableView *tableContent;

@property (weak, nonatomic) IBOutlet UIView *viewContentMask;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintMenuContainerTrailing;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintContentViewLeading;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *constraintContentViewWidth;

- (IBAction)clickShowHideMenu:(id)sender;

- (IBAction)clickAddNewConvert:(id)sender;

- (IBAction)clickSwitchConvertRule:(id)sender;

@end
