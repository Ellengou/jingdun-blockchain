//
//  QKLImagePickerViewController.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLImagePickerViewController.h"
#import "QKLLanguages.h"

@interface QKLImagePickerViewController ()<UIActionSheetDelegate, UINavigationControllerDelegate, UIImagePickerControllerDelegate> {
    UIImage *newAvatarImage;
    NSData *newAvatarData;
}

@property (weak, nonatomic) UIViewController *parentVC;

@property (copy, nonatomic) ImagePickerSuccessBlock successBlock;

@property (copy, nonatomic) ImagePickerFailedBlock failedBlock;

@end

@implementation QKLImagePickerViewController

-(void)loadView {
    UIView *view = [[UIView alloc] initWithFrame:[UIScreen mainScreen].bounds];
    view.backgroundColor = [UIColor clearColor];
    self.view = view;
}

- (void)addPickertoParentVC:(UIViewController *)parentVC successBlock:(ImagePickerSuccessBlock)successBlock failedBlock:(ImagePickerFailedBlock)failedBlock {
    _parentVC = parentVC;
    _successBlock = successBlock;
    _failedBlock = failedBlock;
    self.view.frame = parentVC.view.frame;
    [parentVC.view addSubview:self.view];
    [parentVC addChildViewController:self];
    if (![UIImagePickerController isCameraDeviceAvailable:UIImagePickerControllerCameraDeviceRear]
        && ![UIImagePickerController isCameraDeviceAvailable:UIImagePickerControllerCameraDeviceFront]) {
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.delegate = self;
        imagePicker.allowsEditing = YES;
        
        [self presentViewController:imagePicker animated:YES completion:nil];
        
        return;
    }
    
    UIActionSheet *actionSheet = [[UIActionSheet alloc]
                                  initWithTitle:lang_pick_avatar
                                  delegate:self
                                  cancelButtonTitle:lang_cancel
                                  destructiveButtonTitle:nil
                                  otherButtonTitles:lang_camera, lang_photos, nil];
    [actionSheet showInView:self.view];
}

#pragma mark - UIActionSheetDelegate Methods
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    NSString *btnTitle = [actionSheet buttonTitleAtIndex:buttonIndex];
    if ([btnTitle isEqualToString:lang_cancel]) {
        [self.view removeFromSuperview];
        [self removeFromParentViewController];
        return;
    }
    
    if ([actionSheet.title isEqualToString:lang_pick_avatar]) {//更改头像
        
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        
        if ([btnTitle isEqualToString:lang_camera]) {
            imagePicker.sourceType = UIImagePickerControllerSourceTypeCamera;
        } else if ([btnTitle isEqualToString:lang_photos]) {
            imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        } else {
            return;
        }
        imagePicker.delegate = self;
        imagePicker.allowsEditing = YES;
        [self presentViewController:imagePicker animated:YES completion:nil];
    }
}


#pragma UIImagePickerControllerDelegate method

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    newAvatarImage = [info objectForKey:UIImagePickerControllerEditedImage];
    NSString *newAvatarPath = [((NSURL*)[info objectForKey:UIImagePickerControllerReferenceURL]).absoluteString copy];
    if (picker.sourceType == UIImagePickerControllerSourceTypePhotoLibrary || picker.sourceType == UIImagePickerControllerSourceTypeSavedPhotosAlbum) {
        if ([newAvatarPath hasSuffix:@"PNG"]) {
            newAvatarData = UIImagePNGRepresentation(newAvatarImage);
        } else if ([newAvatarPath hasSuffix:@"JPG"]) {
            newAvatarData = UIImageJPEGRepresentation(newAvatarImage, 1.0);
        } else {
            [[[UIAlertView alloc] initWithTitle:lang_hint message:lang_img_format_err delegate:nil cancelButtonTitle:lang_ok otherButtonTitles: nil] show];
            return;
        }
    } else if(picker.sourceType == UIImagePickerControllerSourceTypeCamera) {
        newAvatarData = UIImageJPEGRepresentation(newAvatarImage, 1.0);
    }
    
    if (newAvatarData) {
        //已成功获取图片
        _successBlock(newAvatarData);
    } else {
        _failedBlock(lang_img_pick_err);
    }
    [picker dismissViewControllerAnimated:YES completion:nil];
    [self.view removeFromSuperview];
    [self removeFromParentViewController];
    _parentVC = nil;
    _successBlock = nil;
    _failedBlock = nil;
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [picker dismissViewControllerAnimated:YES completion:nil];
    if (_failedBlock) {
        _failedBlock(nil);
    }
    [self.view removeFromSuperview];
    [self removeFromParentViewController];
    _parentVC = nil;
    _successBlock = nil;
    _failedBlock = nil;
}

@end
