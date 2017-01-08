//
//  CALayer+Color.m
//  huimeicity
//
//  Created by dapeng on 16/4/17.
//  Copyright © 2016年 nayuntec. All rights reserved.
//

#import "CALayer+Color.h"

@implementation CALayer (Color)

- (void)setBorderUIColor:(UIColor *)color {
    self.borderColor = color.CGColor;
}

- (UIColor*)borderUIColor {
    return [UIColor colorWithCGColor:self.borderColor];
}

@end
