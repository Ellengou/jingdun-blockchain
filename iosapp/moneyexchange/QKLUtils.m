//
//  QKLUtils.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLUtils.h"
#import "QKLServerClient.h"
#import "QKLUserLoginViewController.h"
#import "QKLCompleteInfoViewController.h"
#import "QKLTextAttachment.h"

@implementation QKLUtils

+ (NSInteger)getResponseCode:(NSDictionary *)response {
    NSInteger rc = [[response objectForKey:tag_rc] integerValue];
    if (rc == 1000) {
        [[QKLUserManager shareInstanceWithVC:nil] logOutLocal];
    }
    return rc;
}

+ (NSString *)getResponseMsg:(NSDictionary *)response {
    NSString *msg = [response objectForKey:tag_msg];
    if (msg && msg.length > 0) {
        return msg;
    } else {
        return lang_unknow_err;
    }
}

+ (UIView *)getTableFooterForLoadingWithWidth:(CGFloat)width {
    UIView *footer = [[UIView alloc] initWithFrame:CGRectMake(0.0f, 0.0f, width, 44.0f)];
    footer.backgroundColor = [UIColor clearColor];
    
    UIActivityIndicatorView *loading = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    loading.frame = CGRectMake(0.0, 0.0, 30.0f, 30.0f);
    [footer addSubview:loading];
    loading.center = footer.center;
    [loading startAnimating];
    
    return footer;
}

+ (UIView *)getNoDataTableFooterWithHeight:(CGFloat)height msg:(NSString *)msg bgColor:(UIColor *)bgColor {
    UIView *footer = [[UIView alloc] initWithFrame:CGRectMake(0.0f, 0.0f, kScreenWidth, height)];
    footer.backgroundColor = bgColor;
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, kScreenWidth, 20.0f)];
    [footer addSubview:label];
    label.center = footer.center;
    label.textAlignment = NSTextAlignmentCenter;
    label.textColor = [UIColor colorWithRGB:0x858585];
    label.font = [UIFont systemFontOfSize:13.0f];
    label.attributedText = [QKLUtils getNoDataMsg:msg];
    
    return footer;
}

+ (UIImage *)cacheImageNamed:(NSString *)name {//bundle里面的资源
    
    if (!name) return nil;
    UIImage *image = [[YYImageCache sharedCache] getImageForKey:name withType:YYImageCacheTypeMemory];
    if (image) return image;
    image = [UIImage imageNamed:name];
    image = [image imageByDecoded];
    if (!image) return nil;
    [[YYImageCache sharedCache] setImage:image imageData:nil forKey:name withType:YYImageCacheTypeMemory];
    return image;
}

+ (NSString *)getUpdateTimeByTime:(long long)lTime isInSecond:(BOOL)isInSecond {
    if (!isInSecond) {
        lTime /= 1000;
    }
    
    //    NSTimeInterval now = [[NSDate date] timeIntervalSince1970];
    
    // 今天的零点的时间
    //    NSDateFormatter *formater = [[NSDateFormatter alloc] init];
    //    [formater setDateStyle:NSDateFormatterMediumStyle];
    //    [formater setTimeZone:[NSTimeZone localTimeZone]];
    //    [formater setTimeZone:[NSTimeZone timeZoneWithName:@"GMT"]];
    //    NSString *dateStr = [formater stringFromDate:[NSDate date]];
    //    NSDate *rdate = [formater dateFromString:dateStr];
    //    long long today = (long long)now - [rdate timeIntervalSince1970];
    // 和当前时间的差时
    //    long long lDura = (long long)now - lTime;
    
    NSString *str = @"";
    /*if (lDura <= 0ll && lDura >= -3600ll) {
     str = [NSString stringWithFormat:@"刚刚"];
     } else if (lDura < 60 && lDura > 0) {
     str = [NSString stringWithFormat:@"%lld秒前", lDura];
     } else if (lDura < 3600 && lDura > 0) {
     str = [NSString stringWithFormat:@"%lld分钟前", lDura / 60];
     } else if (lDura < today && lDura > 0) {//在今天之内
     NSDate *thatDate = [NSDate dateWithTimeIntervalSince1970:lTime];
     NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
     dateFormat.dateFormat = @"HH:mm";
     str = [dateFormat stringFromDate:thatDate];
     str = [NSString stringWithFormat:@"今天 %@", str];
     } else {*/
    NSDate *thatDate = [NSDate dateWithTimeIntervalSince1970:lTime];
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    dateFormat.dateFormat = @"yyyy/MM/dd HH:mm";
    str = [dateFormat stringFromDate:thatDate];
    
    return str;
}

+ (float)getAdjustHeightWithBase_H:(float)base_H
{
    if (SCREEN_H_480) {
        return base_H;
    } else if (SCREEN_H_667) {
        return ((float)(375.0f / 320.0f)) * base_H;
    } else if(SCREEN_H_736) {
        return ((float)(414.0f / 320.0f)) * base_H;
    } else{
        return base_H;
    }
}

+(NSString *)strUTF8Encoding:(NSString *)str {
    return [str stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
}

+ (BOOL)isPureNumber:(NSString*)string {
    NSScanner* scan = [NSScanner scannerWithString:string];
    int val;
    return [scan scanInt:&val] || [scan isAtEnd];
}

+ (NSDictionary *)completeUserToken:(NSDictionary *)dict {
    if ([[QKLUserManager shareInstanceWithVC:nil] hasLoggin]) {
        NSMutableDictionary *temp = [NSMutableDictionary dictionaryWithDictionary:dict];
        [temp setObject:[[QKLUserManager shareInstanceWithVC:nil] getUserToken] forKey:tag_token];
        return  [temp copy];
    } else {
        return dict;
    }
}


+ (void)jumpToLoginFromViewController:(UIViewController *)vc {
    [vc presentViewController:[[QKLUserLoginViewController alloc] initWithNibName:@"QKLUserLoginViewController" bundle:nil] animated:YES completion:NULL];
}

+ (BOOL)checkLoginStatusForViewController:(UIViewController *)vc {
    if (![[QKLUserManager shareInstanceWithVC:vc] hasLoggin]) {
        [QKLUtils jumpToLoginFromViewController:vc];
        return YES;
    }
    return NO;
}

+ (NSAttributedString *)getNoDataMsg:(NSString *)msg {
    NSMutableAttributedString *mutableAtt = [[NSMutableAttributedString alloc] initWithString:msg];
    
    QKLTextAttachment *imgPro = [[QKLTextAttachment alloc] init];
    imgPro.image = [QKLUtils cacheImageNamed:@"img_warning.png"];
    NSAttributedString *attachGPString = [NSAttributedString attributedStringWithAttachment:imgPro];
    NSMutableAttributedString *spaceAtt = [[NSMutableAttributedString alloc] initWithString: @"  "];
    [spaceAtt insertAttributedString:attachGPString atIndex:0];
    [mutableAtt insertAttributedString:[spaceAtt copy] atIndex:0];
    return [mutableAtt copy];
}

+ (void)addNoDataViewToSuperView:(UIView *)superView msg:(NSString *)msg{
    UIView *container = [[UIView alloc] initWithFrame:superView.bounds];
    container.backgroundColor = [UIColor clearColor];
    UILabel *label = [UILabel new];
    [container addSubview:label];
    label.center = container.center;
    label.textColor = [UIColor colorWithRGB:0x626262];
    label.font = [UIFont systemFontOfSize:14.0f];
    label.attributedText = [QKLUtils getNoDataMsg:msg];
    
    [superView addSubview:container];
}

+ (BOOL) isAllDigits:(NSString *)str
{
    NSCharacterSet* nonNumbers = [[NSCharacterSet decimalDigitCharacterSet] invertedSet];
    NSRange r = [str rangeOfCharacterFromSet: nonNumbers];
    return r.location == NSNotFound;
}

+ (BOOL) isAlphaNumeric:(NSString *)str {
    NSCharacterSet* nonNumbers = [[NSCharacterSet alphanumericCharacterSet] invertedSet];
    NSRange r = [str rangeOfCharacterFromSet: nonNumbers];
    return r.location == NSNotFound;
}
@end
