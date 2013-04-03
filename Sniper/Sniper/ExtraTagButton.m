//
//  ExtraTagButton.m
//  Sniper
//
//  Created by Jacob Hanshaw on 4/3/13.
//  Copyright (c) 2013 Jacob Hanshaw. All rights reserved.
//

#import "ExtraTagButton.h"

@implementation ExtraTagButton

@synthesize section, row;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.frame = frame;
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
