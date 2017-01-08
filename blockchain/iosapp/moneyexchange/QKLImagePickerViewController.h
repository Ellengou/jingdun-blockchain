//
//  QKLImagePickerViewController.h
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLViewController.h"

typedef void(^ImagePickerSuccessBlock)(NSData *imgData);
typedef void(^ImagePickerFailedBlock)(NSString *errMsg);

@interface QKLImagePickerViewController : UIViewController

- (void)addPickertoParentVC:(UIViewController *)parentVC successBlock:(ImagePickerSuccessBlock)successBlock failedBlock:(ImagePickerFailedBlock)failedBlock;

@end
