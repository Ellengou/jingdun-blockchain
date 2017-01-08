//
//  QKLTextAttachment.m
//  moneyexchange
//
//  Created by dapeng on 16/10/15.
//  Copyright © 2016年 qukuailian. All rights reserved.
//

#import "QKLTextAttachment.h"

@implementation QKLTextAttachment

- (CGRect)attachmentBoundsForTextContainer:(NSTextContainer *)textContainer proposedLineFragment:(CGRect)lineFrag glyphPosition:(CGPoint)position characterIndex:(NSUInteger)charIndex {
    
    return CGRectMake(0.f, -3.f, (lineFrag.size.height*self.image.size.width) / self.image.size.height, lineFrag.size.height);
    
}

@end
