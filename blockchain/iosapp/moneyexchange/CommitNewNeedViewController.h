//
//  CommitNewNeedViewController.h
//  moneyexchange
//
//  Created by dapeng on 2016/10/27.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "QKLUtils.h"
#import "QKLServerClient.h"

@interface CommitNewNeedViewController : UIViewController

@property (nonatomic, assign) ConvertMoneyType moneyType;

@property (nonatomic, assign) CGFloat money;

@property (nonatomic, strong) NSDictionary *itemaddResult;

@property (weak, nonatomic) IBOutlet UITableView *theTable;

- (IBAction)clickedBack:(id)sender;

@end
