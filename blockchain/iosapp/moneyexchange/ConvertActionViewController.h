//
//  ConvertActionViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/16.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"
#import "QKLConvertInfoNode.h"

@interface ConvertActionViewController : QKLViewController

@property (strong, nonatomic) NSArray<QKLConvertInfoNode *> *infoNodeList;

@property (weak, nonatomic) IBOutlet UITableView *tableConvertDetail;

- (IBAction)clickBack:(id)sender;
@end
